/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.key;

import hera.example.AbstractExample;
import hera.key.AergoKey;

public class ImportingKey extends AbstractExample {

  @Override
  public void run() throws Exception {
    // recover key with encrypted one
    String encrypted = "47R1csG1CiCQ8nXLXPGduSQTwQkG8sbr7k31m5yCuKDaFyBRf84tU7cMiqjXJmVAPbcBxEXSZ";
    String decryptPassword = "password";
    AergoKey recoverted = AergoKey.of(encrypted, decryptPassword);
    System.out.println("Recovered key: " + recoverted);
  }

  public static void main(String[] args) throws Exception {
    new ImportingKey().run();
  }

}
