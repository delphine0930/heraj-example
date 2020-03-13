/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.api.model.Block;
import hera.api.model.BlockHash;
import hera.api.model.BlockMetadata;
import hera.api.model.StreamObserver;
import hera.api.model.Subscription;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import java.util.List;

public class BlockOperationExample {

  public static void main(String[] args) throws Exception {
    // create client
    AergoClient client = new AergoClientBuilder()
        .withEndpoint("testnet-api.aergo.io:7845")
        .withNonBlockingConnect()
        .build();

    // common
    BlockHash blockHash = BlockHash.of("DN9TvryaThbJneSpzaXp5ZsS4gE3UMzKfaXC4x8L5qR1");
    long height = 27_066_653L;

    /* block by hash */
    Block blockByHash = client.getBlockOperation().getBlock(blockHash);

    /* block by height */
    Block blockByHeight = client.getBlockOperation().getBlock(height);

    /* block metadata by hash */
    BlockMetadata metadataByHash = client.getBlockOperation().getBlockMetadata(blockHash);

    /* block metadata by height */
    BlockMetadata metadataByHeight = client.getBlockOperation().getBlockMetadata(height);

    /* block metadatas by from hash to 100 previous */
    List<BlockMetadata> metadatasByHash = client.getBlockOperation()
        .listBlockMetadatas(blockHash, 100);

    /* block metadatas by from height to 100 previous */
    List<BlockMetadata> metadatasByHeight = client.getBlockOperation()
        .listBlockMetadatas(height, 100);

    /* subscribe new block */

    // make a subscription
    Subscription<Block> blockSubscription = client.getBlockOperation()
        .subscribeNewBlock(new StreamObserver<Block>() {
          @Override
          public void onNext(Block value) {
            System.out.println("Next: " + value);
          }

          @Override
          public void onError(Throwable t) {
          }

          @Override
          public void onCompleted() {
          }
        });

    // wait for a while
    Thread.sleep(2000L);

    // unsubscribe block stream
    blockSubscription.unsubscribe();

    /* subscribe new block metadata */

    // make a subscription
    Subscription<BlockMetadata> metadataSubscription = client
        .getBlockOperation().subscribeNewBlockMetadata(new StreamObserver<BlockMetadata>() {
          @Override
          public void onNext(BlockMetadata value) {
            System.out.println("Next: " + value);
          }

          @Override
          public void onError(Throwable t) {

          }

          @Override
          public void onCompleted() {
          }
        });

    // wait for a while
    Thread.sleep(2000L);

    // unsubscribe block metadata stream
    metadataSubscription.unsubscribe();

    // close client
    client.close();
  }

}
