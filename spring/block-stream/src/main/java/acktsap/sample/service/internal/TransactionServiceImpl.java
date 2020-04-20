/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.service.internal;

import acktsap.sample.service.TransactionService;
import acktsap.sample.stream.BlockStream;
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
import hera.key.AergoKey;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TransactionServiceImpl implements TransactionService {

  @Autowired
  protected Supplier<ChainIdHash> chainIdHashSupplier;

  protected final Object lock = new Object();
  protected volatile boolean hasBinded = false;

  @Autowired
  protected NonceProvider nonceProvider;

  @Autowired
  protected AergoKey richKey;

  @Autowired
  protected BlockStream blockStream;

  @Autowired
  protected AergoClient aergoClient;

  @Override
  public CompletableFuture<TxHash> send(final AccountAddress recipient, final Aer amount) {
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
    final RawTransaction rawTransaction = RawTransaction.newBuilder()
        .chainIdHash(chainIdHashSupplier.get())
        .from(richKey.getAddress())
        .to(recipient)
        .amount(amount)
        .nonce(nonceProvider.incrementAndGetNonce(richKey.getAddress()))
        .fee(Fee.EMPTY)
        .build();
    final Transaction signed = richKey.sign(rawTransaction);

    // submit tx hash
    CompletableFuture<TxHash> future = blockStream.submit(signed.getHash());

    // commit signed tx
    aergoClient.getTransactionOperation().commit(signed);

    return future;
  }

  protected void bindRichState() {
    final AccountState state = aergoClient.getAccountOperation()
        .getState(richKey.getAddress());
    nonceProvider.bindNonce(state);
  }

}
