/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.config;

import hera.api.model.ChainIdHash;
import hera.api.transaction.NonceProvider;
import hera.api.transaction.SimpleNonceProvider;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AergoConfig {

  @Value("${aergo.server.hostname}")
  protected String hostname;

  @Value("${aergo.server.port}")
  protected String port;

  @Value("${aergo.account.wif}")
  protected String walletImportFormat;

  @Value("${aergo.account.password}")
  protected String password;

  @Bean
  public AergoClient aergoClient() {
    return new AergoClientBuilder()
        .withEndpoint(hostname + ":" + port)
        .withPlainText()
        .build();
  }

  @Bean
  public Supplier<ChainIdHash> chainIdHashSupplier() {
    return new Supplier<ChainIdHash>() {

      protected final Object lock = new Object();
      protected ChainIdHash cache;

      @Override
      public ChainIdHash get() {
        if (null == cache) {
          synchronized (lock) {
            if (null == cache) {
              cache = aergoClient().getBlockchainOperation().getChainIdHash();
            }
          }
        }
        return cache;
      }
    };
  }

  @Bean
  public NonceProvider nonceProvider() {
    return new SimpleNonceProvider();
  }

  @Bean
  public AergoKey richKey() {
    return AergoKey.of(walletImportFormat, password);
  }

}
