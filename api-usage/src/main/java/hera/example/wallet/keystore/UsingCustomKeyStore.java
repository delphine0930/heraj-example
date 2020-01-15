/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.wallet.keystore;

import hera.example.AbstractExample;
import hera.keystore.KeyStore;
import hera.wallet.WalletApi;
import hera.wallet.WalletApiFactory;

public class UsingCustomKeyStore extends AbstractExample {

  @Override
  public void run() throws Exception {
    // create in memory keystore
    KeyStore keyStore = new CustomKeyStore();
    System.out.println("Keystore: " + keyStore);

    // create walletapi
    WalletApi walletApi = new WalletApiFactory().create(keyStore);
    System.out.println("Walletapi with custom keystore: " + walletApi);
  }

  public static void main(String[] args) throws Exception {
    new UsingCustomKeyStore().run();
  }

}
