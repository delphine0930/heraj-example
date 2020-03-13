/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.client;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.Aer.Unit;
import hera.api.model.BytesValue;
import hera.api.model.ChainIdHash;
import hera.api.model.ContractAddress;
import hera.api.model.ContractDefinition;
import hera.api.model.ContractFunction;
import hera.api.model.ContractInterface;
import hera.api.model.ContractInvocation;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.StateVariable;
import hera.api.transaction.ContractInvocationPayloadConverter;
import hera.api.transaction.PayloadConverter;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MakingTransactionExample {

  protected static ContractInterface dummyContractInterface() {
    final ContractAddress address =
        ContractAddress.of("AmJaNDXoPbBRn9XHh9onKbDKuAzj88n5Bzt7KniYA78qUEc5EwBd");
    final String version = "v1";
    final String language = "lua";
    final ContractFunction set = new ContractFunction("set", false, false, true);
    final ContractFunction get = new ContractFunction("get", false, true, false);
    final List<ContractFunction> functions = Arrays.asList(set, get);
    final List<StateVariable> stateVariables = Collections.emptyList();
    final ContractInterface contractInterface =
        new ContractInterface(address, version, language, functions, stateVariables);
    return contractInterface;
  }

  public static void main(String[] args) {
    AergoKey aergoKey = new AergoKeyGenerator().create();

    /* plain transaction */

    RawTransaction plainTransaction = RawTransaction.newBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .to(aergoKey.getAddress())
        .amount(Aer.AERGO_ONE)
        .nonce(1L)
        .fee(Fee.ZERO)
        .payload(BytesValue.of("payload".getBytes()))
        .build();


    /* deploy contract */

    ContractDefinition deployTarget = ContractDefinition.newBuilder()
        .encodedContract(
            "FppTEQaroys1N4P8RcAYYiEhHaQaRE9fzANUx4q2RHDXaRo6TYiTa61n25JcV19grEhpg8qdCWVdsDE2yVfuTKxxcdsTQA2B5zTfxA4GqeRqYGYgWJpj1geuLJAn1RjotdRRxSS1BFA6CAftxjcgiP6WUHacmgtNzoWViYESykhjqVLdmTfV12d44wfh9YAgQ57aRkLNCPkujbnJhdhHEtY1hrJYLCxUDBveqVcDhrrvcHtjDAUcZ5UMzbg6qR1kthGB1Lua6ymw1BmfySNtqb1b6Hp92UPMa7gi5FpAXF5XgpQtEbYDXMbtgu5XtXNhNejrtArcekmjrmPXRoTnMDGUQFcALtnNCrgSv2z5PiXP1coGEbHLTTbxkmJmJz6arEfsb6J1Dv7wnvgysDFVApcpABfwMjHLmnEGvUCLthRfHNBDGydx9jvJQvismqdpDfcEaNBCo5SRMCqGS1FtKtpXjRaHGGFGcTfo9axnsJgAGxLk")
        .amount(Aer.ZERO)
        .constructorArgs(1, 2)
        .build();
    RawTransaction deployTransaction = RawTransaction.newDeployContractBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .nonce(1L)
        .definition(deployTarget)
        .fee(Fee.ZERO)
        .build();


    /* invoke contract */

    // build
    ContractInterface contractInterface = dummyContractInterface();
    ContractInvocation execute = contractInterface.newInvocationBuilder()
        .function("set")
        .args("key", "123")
        .delegateFee(false)
        .build();
    RawTransaction executeTransaction = RawTransaction.newInvokeContractBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .nonce(1L)
        .invocation(execute)
        .fee(Fee.ZERO)
        .build();

    // parse
    PayloadConverter<ContractInvocation> invocationConverter =
        new ContractInvocationPayloadConverter();
    BytesValue payload = executeTransaction.getPayload();
    ContractInvocation parsedInvocation = invocationConverter.parseToModel(payload);


    /* redeploy contract */

    // build
    ContractDefinition reDeployTarget = ContractDefinition.newBuilder()
        .encodedContract(
            "FppTEQaroys1N4P8RcAYYiEhHaQaRE9fzANUx4q2RHDXaRo6TYiTa61n25JcV19grEhpg8qdCWVdsDE2yVfuTKxxcdsTQA2B5zTfxA4GqeRqYGYgWJpj1geuLJAn1RjotdRRxSS1BFA6CAftxjcgiP6WUHacmgtNzoWViYESykhjqVLdmTfV12d44wfh9YAgQ57aRkLNCPkujbnJhdhHEtY1hrJYLCxUDBveqVcDhrrvcHtjDAUcZ5UMzbg6qR1kthGB1Lua6ymw1BmfySNtqb1b6Hp92UPMa7gi5FpAXF5XgpQtEbYDXMbtgu5XtXNhNejrtArcekmjrmPXRoTnMDGUQFcALtnNCrgSv2z5PiXP1coGEbHLTTbxkmJmJz6arEfsb6J1Dv7wnvgysDFVApcpABfwMjHLmnEGvUCLthRfHNBDGydx9jvJQvismqdpDfcEaNBCo5SRMCqGS1FtKtpXjRaHGGFGcTfo9axnsJgAGxLk")
        .amount(Aer.ZERO)
        .constructorArgs(1, 2)
        .build();
    RawTransaction reDeployTransaction = RawTransaction.newReDeployContractBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .creator(aergoKey.getAddress()) // must be creator
        .nonce(1L)
        .contractAddress(ContractAddress.of("AmJaNDXoPbBRn9XHh9onKbDKuAzj88n5Bzt7KniYA78qUEc5EwBd"))
        .definition(reDeployTarget)
        .fee(Fee.ZERO)
        .build();


    /* create name */

    // build
    RawTransaction createNameTransaction = RawTransaction.newCreateNameTxBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .nonce(1L)
        .name("namenamename")
        .build();


    /* create name */

    // build
    RawTransaction updateNameTransaction = RawTransaction.newUpdateNameTxBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .nonce(1L)
        .name("namenamename")
        .nextOwner(AccountAddress.of("AmgVbUZiReUVFXdYb4UVMru4ZqyicSsFPqumRx8LfwMKLFk66SNw"))
        .build();


    /* stake */

    RawTransaction stakeTransaction = RawTransaction.newStakeTxBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .amount(Aer.of("10000", Unit.AERGO))
        .nonce(1L)
        .build();


    /* unstake */

    // build
    RawTransaction unstakeTransaction = RawTransaction.newUnstakeTxBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .amount(Aer.of("10000", Unit.AERGO))
        .nonce(1L)
        .build();


    /* vote */

    // build
    RawTransaction voteTransaction = RawTransaction.newVoteTxBuilder()
        .chainIdHash(ChainIdHash.of(BytesValue.EMPTY))
        .from(aergoKey.getAddress())
        .nonce(1L)
        .voteId("voteBP")
        .candidates(Arrays.asList("123", "456"))
        .build();
  }

}
