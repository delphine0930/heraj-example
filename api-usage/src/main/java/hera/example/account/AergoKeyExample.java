/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.account;

import hera.api.model.Aer;
import hera.api.model.BytesValue;
import hera.api.model.ChainIdHash;
import hera.api.model.EncryptedPrivateKey;
import hera.api.model.RawTransaction;
import hera.api.model.Signature;
import hera.api.model.Transaction;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import hera.key.AergoSignVerifier;
import hera.key.Verifier;

public class AergoKeyExample {

  public static void main(String[] args) {
    /* new */
    AergoKey aergoKey = new AergoKeyGenerator().create();
    System.out.println(aergoKey);

    /* export as wif */
    EncryptedPrivateKey wif = aergoKey.exportAsWif("password");
    System.out.println(wif);

    /* export as keyformat */
    // TODO

    /* import with wif */
    EncryptedPrivateKey importedWif = EncryptedPrivateKey
        .of("47btMyQmmWddJmEigUp8HjUPam94Jjtf6eG6SW74r61YmbcJGyoxhwTBa8XhVBQ9wYm468DED");
    AergoKey imported = AergoKey.of(importedWif, "password");
    System.out.println(imported);

    /* import with keyformat */
    // TODO

    /* sign transaction */
    RawTransaction rawTransaction = RawTransaction.newBuilder(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .to(aergoKey.getAddress())
        .amount(Aer.AERGO_ONE)
        .nonce(1L)
        .build();
    Transaction transaction = aergoKey.sign(rawTransaction);
    System.out.println(transaction);

    /* sign message */
    Signature signature = aergoKey.signMessage(BytesValue.of("I'm message".getBytes()));
    System.out.println(signature);

    /* verify transaction */
    Verifier verifier = new AergoSignVerifier();
    boolean verifyTx = verifier.verify(transaction);
    System.out.println(verifyTx);

    /* verify message */
    // TODO
  }

}
