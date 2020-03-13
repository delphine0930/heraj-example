/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.api.model.Aer;
import hera.api.model.BytesValue;
import hera.api.model.ChainIdHash;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;

public class TransactionOperationExample {

  public static void main(String[] args) {
    // create a client
    AergoClient client = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .build();

    /* commit */
    AergoKey aergoKey = new AergoKeyGenerator().create();
    RawTransaction rawTransaction = RawTransaction.newBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .to(aergoKey.getAddress())
        .amount(Aer.AERGO_ONE)
        .nonce(1L)
        .fee(Fee.ZERO)
        .payload(BytesValue.of("payload".getBytes()))
        .build();
    Transaction signed = aergoKey.sign(rawTransaction);
    TxHash commited = client.getTransactionOperation().commit(signed);

    /* get tx */
    TxHash txHash = TxHash.of("EGXNDgjY2vQ6uuP3UF3dNXud54dF4FNVY181kaeQ26H9");
    Transaction getResult = client.getTransactionOperation().getTransaction(txHash);

    // close client
    client.close();
  }

}
