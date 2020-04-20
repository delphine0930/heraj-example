/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.stream.internal;

import acktsap.sample.stream.BlockStream;
import hera.api.model.Block;
import hera.api.model.StreamObserver;
import hera.api.model.Subscription;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class BlockStreamImpl implements BlockStream {

  protected final Map<TxHash, CompletableFuture<TxHash>> hash2Submited = new ConcurrentHashMap<>();

  protected final Object lock = new Object();
  protected volatile Subscription<Block> subscription;

  @Autowired
  protected AergoClient client;

  @PostConstruct
  protected void init() {
    subscription = makeNewSubscription();
  }

  @PreDestroy
  protected void destroy() {
    // unsubscribe before destroying
    if (null != subscription) {
      subscription.unsubscribe();
    }
  }

  @Override
  public CompletableFuture<TxHash> submit(final TxHash txHash) {
    // make a subscription if it's in unsubscribed state
    if (subscription.isUnsubscribed()) {
      synchronized (lock) {
        if (subscription.isUnsubscribed()) {
          subscription = makeNewSubscription();
        }
      }
    }

    final CompletableFuture<TxHash> future = new CompletableFuture<>();
    hash2Submited.put(txHash, future);
    return future;
  }

  private Subscription<Block> makeNewSubscription() {
    return client.getBlockOperation().subscribeNewBlock(new StreamObserver<Block>() {
      @Override
      public void onNext(final Block block) {
        System.out.println("New block: " + block.getHash());
        block.getTransactions().stream().map(Transaction::getHash)
            .forEach(hash -> {
              final CompletableFuture<TxHash> submitted = hash2Submited.get(hash);
              if (null != submitted) {
                // complete submited hash
                System.out.println("Complete submitted hash: " + hash);
                submitted.complete(hash);
                hash2Submited.remove(hash);
              }
            });
      }

      @Override
      public void onError(final Throwable t) {
        System.err.println("Error: " + t);
      }

      @Override
      public void onCompleted() {
        System.err.println("Complete");
      }
    });
  }

}
