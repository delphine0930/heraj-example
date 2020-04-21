/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.service.internal;

import hera.api.model.AccountAddress;
import hera.api.model.AccountState;
import hera.api.model.Aer;
import hera.api.model.ChainIdHash;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.api.transaction.NonceProvider;
import hera.client.AergoClient;
import hera.example.service.TransactionService;
import hera.example.stream.BlockStream;
import hera.key.AergoKey;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TransactionServiceImpl implements TransactionService {

  protected final Object lock = new Object();
  protected volatile boolean hasBinded = false;

  @Autowired
  protected Supplier<ChainIdHash> chainIdHashSupplier;

  @Autowired
  protected NonceProvider nonceProvider;

  @Autowired
  protected AergoKey richKey;

  @Autowired
  protected BlockStream blockStream;

  @Autowired
  protected AergoClient aergoClient;

  @Override
  public CompletableFuture<TxHash> send(AccountAddress recipient, Aer amount) {
    // bind nonce of rich key once
    if (!hasBinded) {
      synchronized (lock) {
        if (!hasBinded) {
          bindRichState();
          hasBinded = true;
        }
      }
    }

    // make a transaction
    RawTransaction rawTransaction = RawTransaction.newBuilder()
        .chainIdHash(chainIdHashSupplier.get())
        .from(richKey.getAddress())
        .to(recipient)
        .amount(amount)
        .nonce(nonceProvider.incrementAndGetNonce(richKey.getAddress()))
        .fee(Fee.EMPTY)
        .build();
    Transaction signed = richKey.sign(rawTransaction);

    // tx hash
    TxHash txHash = signed.getHash();

    // submit tx hash before commit
    CompletableFuture<TxHash> future = blockStream.submit(txHash);

    // commit signed tx
    try {
      aergoClient.getTransactionOperation().commit(signed);
    } catch (Exception e) {
      // unsubmit on commit error to prevent memory leak
      blockStream.unsubmit(txHash);
      throw new IllegalStateException(e);
    }

    return future;
  }

  protected void bindRichState() {
    AccountState state = aergoClient.getAccountOperation()
        .getState(richKey.getAddress());
    nonceProvider.bindNonce(state);
  }

}
