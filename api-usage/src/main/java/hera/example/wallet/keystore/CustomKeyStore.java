/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.wallet.keystore;

import hera.api.model.Authentication;
import hera.api.model.EncryptedPrivateKey;
import hera.api.model.Identity;
import hera.key.AergoKey;
import hera.key.Signer;
import hera.keystore.KeyStore;
import java.util.List;

public class CustomKeyStore implements KeyStore {

  @Override
  public void save(Authentication authentication, AergoKey key) {
    // TODO Auto-generated method stub

  }

  @Override
  public Signer load(Authentication authentication) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void remove(Authentication authentication) {
    // TODO Auto-generated method stub

  }

  @Override
  public EncryptedPrivateKey export(Authentication authentication, String password) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Identity> listIdentities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void store(String path, char[] password) {
    // TODO Auto-generated method stub

  }

}
