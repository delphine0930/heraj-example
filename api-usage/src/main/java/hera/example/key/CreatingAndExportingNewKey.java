/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.key;

import hera.example.AbstractExample;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;

public class CreatingAndExportingNewKey extends AbstractExample {

  @Override
  public void run() throws Exception {
    // create new key
    AergoKey key = new AergoKeyGenerator().create();
    System.out.println("Created aergo key: " + key);

    // export key
    String exported = key.export("password").getEncoded();
    System.out.println("Exported aergo key: " + exported);
  }

  public static void main(String[] args) throws Exception {
    new CreatingAndExportingNewKey().run();
  }

}
