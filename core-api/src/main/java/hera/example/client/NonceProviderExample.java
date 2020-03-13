/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.api.model.Aer;
import hera.api.model.ChainIdHash;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.api.transaction.NonceProvider;
import hera.api.transaction.SimpleNonceProvider;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

public class NonceProviderExample {

  public static void main(String[] args) throws Exception {
    // create client
    AergoClient client = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .build();
    AergoKey key = new AergoKeyGenerator().create();

    /* create nonce provider with capacity 100 */
    NonceProvider nonceProvider = new SimpleNonceProvider(100);

    /* bind nonce */
    nonceProvider.bindNonce(key.getAddress(), 0L);

    // request
    ChainIdHash chainIdHash = client.getBlockchainOperation().getChainIdHash();
    Function<Long, RawTransaction> txSupplier = (nonce) -> RawTransaction.newBuilder(chainIdHash)
        .from(key.getAddress())
        .to(key.getAddress())
        .amount(Aer.ONE)
        .nonce(nonce)
        .build();
    ExecutorService service = Executors.newCachedThreadPool();
    IntStream.range(0, 100).forEach(i -> {
      service.submit(() -> {
        /* get nonce to use */
        long nonce = nonceProvider.incrementAndGetNonce(key.getAddress());

        RawTransaction rawTransaction = txSupplier.apply(nonce);
        Transaction signed = key.sign(rawTransaction);
        TxHash txHash = client.getTransactionOperation().commit(signed);
        System.out.println(txHash);
      });
    });

    // stop the service
    service.awaitTermination(3000L, TimeUnit.MILLISECONDS);
    service.shutdown();

    long lastUsedNonce = nonceProvider.getLastUsedNonce(key.getAddress());
    System.out.println("Last nonce: " + lastUsedNonce);

    // close client
    client.close();
  }

}
