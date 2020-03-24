/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import static java.util.Arrays.asList;

import hera.api.model.AccountAddress;
import hera.api.model.AccountState;
import hera.api.model.AccountTotalVote;
import hera.api.model.Aer;
import hera.api.model.Aer.Unit;
import hera.api.model.ElectedCandidate;
import hera.api.model.StakeInfo;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import java.util.List;

public class AccountOperationExample {

  public static void main(String[] args) throws Exception {
    // create client
    AergoClient client = new AergoClientBuilder()
        .withEndpoint("localhost:7845")
        .withNonBlockingConnect()
        .build();
    client.cacheChainIdHash(client.getBlockchainOperation().getChainIdHash());

    // common key
    AergoKey key = new AergoKeyGenerator().create();

    /* get account state */
    AccountAddress addressToGet = AccountAddress
        .of("AmNrsAqkXhQfE6sGxTutQkf9ekaYowaJFLekEm8qvDr1RB1AnsiM");
    AccountState state = client.getAccountOperation().getState(addressToGet);

    /* create name */
    TxHash createNameTxHash = client.getAccountOperation()
        .createName(key, "testtesttest", 1L);

    Thread.sleep(2000L);

    /* update name */
    AccountAddress newOwner = new AergoKeyGenerator().create().getAddress();
    TxHash updateNameTxHash = client.getAccountOperation()
        .updateName(key, "testtesttest", newOwner, 2L);

    /* get name owner */
    AccountAddress nameOwner = client.getAccountOperation().getNameOwner("testtesttest");

    /* stake */
    TxHash stakeTxHash = client.getAccountOperation().stake(key, Aer.of("10000", Unit.AERGO), 3L);

    /* unstake */
    TxHash unStakeTxHash = client.getAccountOperation()
        .unstake(key, Aer.of("10000", Unit.AERGO), 4L);

    /* get stake info */
    AccountAddress addressToGetStakeInfo = AccountAddress
        .of("AmNrsAqkXhQfE6sGxTutQkf9ekaYowaJFLekEm8qvDr1RB1AnsiM");
    StakeInfo stakeInfo = client.getAccountOperation().getStakingInfo(addressToGetStakeInfo);

    /* vote */

    // vote to "voteBP" with value "test"
    client.getAccountOperation().vote(key, "voteBP", asList("test"), 5L);


    /* get vote of specific account */
    AccountAddress addressToGetVoteInfo = AccountAddress
        .of("AmNrsAqkXhQfE6sGxTutQkf9ekaYowaJFLekEm8qvDr1RB1AnsiM");
    AccountTotalVote voteInfo = client.getAccountOperation().getVotesOf(addressToGetVoteInfo);


    /* get vote result */
    // get vote info of "voteBP"
    List<ElectedCandidate> elected = client.getAccountOperation()
        .listElected("voteBP", 23);
    System.out.println(elected);

    // close client
    client.close();
  }

}
