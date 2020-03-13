/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.wallet.keystore;

import hera.example.AbstractExample;
import hera.keystore.JavaKeyStore;
import hera.keystore.KeyStore;
import hera.wallet.WalletApi;
import hera.wallet.WalletApiFactory;

public class CreatingJavaKeyStore extends AbstractExample {

  @Override
  public void run() throws Exception {
    // create new java key store with type PKCS12
    KeyStore keystore = new JavaKeyStore("PKCS12");
    System.out.println("Created keystore: " + keystore);

    // create walletapi with created one
    WalletApi walletApi = new WalletApiFactory().create(keystore);
    System.out.println("Walletapi with created keystore: " + walletApi);

    // save key store to the path
    String path = System.getProperty("java.io.tmpdir") + ".tmpkeystore";
    char[] password = "password".toCharArray();
    keystore.store(path, password);;
  }

  public static void main(String[] args) throws Exception {
    new CreatingJavaKeyStore().run();
  }

}
