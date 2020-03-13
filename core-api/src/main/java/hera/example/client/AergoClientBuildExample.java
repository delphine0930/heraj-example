/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import java.util.concurrent.TimeUnit;

public class AergoClientBuildExample {

  public static void main(String[] args) {
    /* endpoint */
    AergoClient endpointClient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .build();

    /* netty */
    AergoClient nettyClient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .build();

    /* okhttp */
    AergoClient okhttpClient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withBlockingConnect()
        .build();

    /* plaintext */
    AergoClient plainTextCliient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withPlainText()
        .build();

    /* tls */
    AergoClient tlsClient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withTransportSecurity("servername", "${path_to_server_cert}", "${path_to_client_cert}",
            "${path_to_client_key}")
        .build();

    /* retry */
    AergoClient retryClient = new AergoClientBuilder()
        .withRetry(3, 1000L, TimeUnit.MILLISECONDS)
        .build();

    /* timeout */
    AergoClient timeoutClient = new AergoClientBuilder()
        .withTimeout(5000L, TimeUnit.MILLISECONDS)
        .build();

    /* close */
    // create
    AergoClient aergoClient = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withBlockingConnect()
        .withTimeout(10000L, TimeUnit.MILLISECONDS)
        .build();

    // ... do some operations

    // close
    aergoClient.close();

    // autoclose
    try (AergoClient autoClose = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withBlockingConnect()
        .withTimeout(10000L, TimeUnit.MILLISECONDS)
        .build()) {

      // ... do some operations
    }
  }

}
