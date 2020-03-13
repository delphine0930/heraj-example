/*
 * @copyright defined in LICENSE.txt
 */

package hera.example;

import hera.key.AergoKey;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractExample {

  protected String endpoint;
  protected String encrypted;
  protected String password;

  protected AbstractExample() {
    Properties properties = new Properties();
    try (InputStream in = getClass().getResourceAsStream("/aergo.properties")) {
      properties.load(in);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }

    this.endpoint = properties.getProperty("endpoint", "testnet-api.aergo.io:7845");
    this.encrypted = properties.getProperty("encrypted");
    this.password = properties.getProperty("password");

    if (null == this.endpoint) {
      throw new IllegalStateException("encrypted is null");
    }
    if (null == this.encrypted) {
      throw new IllegalStateException("encrypted is null");
    }
    if (null == this.password) {
      throw new IllegalStateException("password is null");
    }
  }

  protected AergoKey supplyKey() {
    return AergoKey.of(encrypted, password);
  }

  public abstract void run() throws Exception;

}
