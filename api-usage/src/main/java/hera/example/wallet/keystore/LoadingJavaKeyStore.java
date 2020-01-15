/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.wallet.keystore;

import hera.api.model.Authentication;
import hera.example.AbstractExample;
import hera.keystore.JavaKeyStore;
import hera.keystore.KeyStore;
import hera.model.KeyAlias;
import hera.wallet.WalletApi;
import hera.wallet.WalletApiFactory;
import java.io.InputStream;

public class LoadingJavaKeyStore extends AbstractExample {

  @Override
  public void run() throws Exception {
    // load new java key store with type PKCS12
    InputStream in = getClass().getResourceAsStream("/keystore.p12");
    char[] password = "password".toCharArray();
    KeyStore keystore = new JavaKeyStore("PKCS12", in, password);
    System.out.println("Loaded keystore: " + keystore);

    // create walletapi with loaded one
    WalletApi walletApi = new WalletApiFactory().create(keystore);
    System.out.println("Walletapi with loaded keystore: " + walletApi);

    // unlocked key
    Authentication authentication = Authentication.of(new KeyAlias("keyalias"), "password");
    boolean unlockResult = walletApi.unlock(authentication);
    System.out.println("Unlock result: " + unlockResult);
    System.out.println("Unlocked account: " + walletApi.getPrincipal());
  }

  public static void main(String[] args) throws Exception {
    new LoadingJavaKeyStore().run();
  }

}
