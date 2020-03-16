/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.api.model.BlockchainStatus;
import hera.api.model.ChainIdHash;
import hera.api.model.ChainInfo;
import hera.api.model.ChainStats;
import hera.api.model.NodeStatus;
import hera.api.model.Peer;
import hera.api.model.PeerMetric;
import hera.api.model.ServerInfo;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import java.util.Collections;
import java.util.List;

public class BlockchainOperationExample {

  public static void main(String[] args) throws Exception {
    // create client
    AergoClient client = new AergoClientBuilder()
        .withEndpoint("testnet-api.aergo.io:7845")
        .withNonBlockingConnect()
        .build();

    /* get chain id hash */
    ChainIdHash chainIdHash = client.getBlockchainOperation().getChainIdHash();

    /* get blockchain status */
    BlockchainStatus blockchainStatus = client.getBlockchainOperation().getBlockchainStatus();

    /* get chain info */
    ChainInfo chainInfo = client.getBlockchainOperation().getChainInfo();

    /* get chain stats */
    ChainStats chainStats = client.getBlockchainOperation().getChainStats();

    /* get node status */
    NodeStatus nodeStatus = client.getBlockchainOperation().getNodeStatus();

    /* get server info */
    List<String> categories = Collections.emptyList();
    ServerInfo serverInfo = client.getBlockchainOperation().getServerInfo(categories);

    /* list peers */

    // filtering itself and hidden
    List<Peer> hideHiddenAndSelfPeers = client.getBlockchainOperation().listPeers(false, false);

    // not filtering itself and hidden
    List<Peer> showAllPeers = client.getBlockchainOperation().listPeers(true, true);

    /* list peers metrics */
    List<PeerMetric> peerMetrics = client.getBlockchainOperation().listPeerMetrics();

    // close client
    client.close();
  }

}
