/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.model;

import static java.util.UUID.randomUUID;

import hera.api.encode.Decoder;
import hera.api.encode.Encoder;
import hera.api.model.BytesValue;
import hera.api.model.Signature;

public class EncodingExample {

  public static void main(String[] args) {
    BytesValue bytesValue = BytesValue.of(randomUUID().toString().getBytes());

    /* encode */

    // to hex
    String hexEncoded = bytesValue.getEncoded(Encoder.Hex);
    System.out.println(hexEncoded);

    // to base58
    String base58Encoded = bytesValue.getEncoded(Encoder.Base58);
    System.out.println(base58Encoded);

    // to base58 with checksum
    String base58WithCheckEncoded = bytesValue.getEncoded(Encoder.Base58Check);
    System.out.println(base58WithCheckEncoded);

    // to base64
    String base64Encoded = bytesValue.getEncoded(Encoder.Base64);
    System.out.println(base64Encoded);


    /* decode */

    // from hex
    BytesValue fromHex = BytesValue
        .of("307864333862306339646363383931666332623735633136643837653063303837373735333031323039323664356361663566323466396634356531636439316639",
            Decoder.Hex);
    System.out.println(fromHex);

    // from base58
    BytesValue fromBase58 = BytesValue
        .of("KszNdKzDtTde6mo4ute7nkawftKUGfEhqCcRkCaEVKpPU4iGEJWSScRUrVyhsmNGQ6KFbueikshvtgJqXhjVLZpRxk",
            Decoder.Base58);
    System.out.println(fromBase58);

    // from base58
    BytesValue fromBase58WithCheck = BytesValue
        .of("38YDwtHjcVvSM56oCVXkGfuTP46QKfXE9E22oYJY5h5YrX6a7rvbuzxf7Y5A5vSpCcab7gsAenQK9rXpNsAWjUm7jKRD86g9",
            Decoder.Base58Check);
    System.out.println(fromBase58WithCheck);

    // from base64
    BytesValue fromBase64 = BytesValue
        .of("MHhkMzhiMGM5ZGNjODkxZmMyYjc1YzE2ZDg3ZTBjMDg3Nzc1MzAxMjA5MjZkNWNhZjVmMjRmOWY0NWUxY2Q5MWY5",
            Decoder.Base64);
    System.out.println(fromBase64);


    /* Signature in base64 */
    BytesValue rawSignature = BytesValue
        .of("MEUCIQDP3ywVXX1DP42nTgM6cF95GFfpoEcl4D9ZP+MHO7SgoQIgdq2UiEiSp23lcPFzCHtDmh7pVzsow5x1s8p5Kz0aN7I=",
            Decoder.Base64);
    Signature signature = Signature.of(rawSignature);
    System.out.println(signature);
  }

}
