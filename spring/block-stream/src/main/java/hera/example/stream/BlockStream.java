/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.stream;

import hera.api.model.TxHash;
import java.util.concurrent.CompletableFuture;

public interface BlockStream {

  /**
   * Submit transaction hash to block stream.
   *
   * @param txHash a transaction hash
   * @return a CompletableFuture which complete after transaction with {@code txHash} is confirmed.
   */
  CompletableFuture<TxHash> submit(TxHash txHash);

  /**
   * Unsubmit submmited transaction hash.
   *
   * @param txHash a transaction hash to unsubmit
   * @return unsubmit result
   */
  boolean unsubmit(TxHash txHash);
}
