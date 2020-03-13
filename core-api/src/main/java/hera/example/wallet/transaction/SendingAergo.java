/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.wallet.transaction;

import static java.util.UUID.randomUUID;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.Authentication;
import hera.api.model.Fee;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.example.AbstractExample;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import hera.keystore.InMemoryKeyStore;
import hera.keystore.KeyStore;
import hera.model.KeyAlias;
import hera.wallet.WalletApi;
import hera.wallet.WalletApiFactory;

public class SendingAergo extends AbstractExample {

  @Override
  public void run() throws Exception {
    // make keystore and save key
    KeyStore keystore = new InMemoryKeyStore();
    KeyAlias alias = new KeyAlias(randomUUID().toString());
    Authentication authentication = Authentication.of(alias, randomUUID().toString());
    AergoKey key = AergoKey.of(encrypted, password);
    keystore.save(authentication, key);

    // make wallet api
    WalletApi walletApi = new WalletApiFactory().create(keystore);

    // make aergo client
    AergoClient aergoClient =
        new AergoClientBuilder().withEndpoint(endpoint).withNonBlockingConnect().build();

    // bind aergo client
    walletApi.bind(aergoClient);;

    // unlock account
    walletApi.unlock(authentication);

    // send tx
    AccountAddress recipient = new AergoKeyGenerator().create().getAddress();
    TxHash txHash = walletApi.transactionApi().send(recipient, Aer.GIGA_ONE, Fee.EMPTY);
    System.out.println("Send transaciton hash: " + txHash);

    // lock an wallet
    walletApi.lock(authentication);

    // close the client
    aergoClient.close();
  }

  public static void main(String[] args) throws Exception {
    new SendingAergo().run();
  }

}
