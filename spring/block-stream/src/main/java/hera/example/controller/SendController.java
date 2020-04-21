/*
 * @copyright defined in LICENSE.txt
 */

package hera.example.controller;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.TxHash;
import hera.example.service.TransactionService;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

  @Autowired
  protected TransactionService transactionService;

  @GetMapping("/send")
  public void send(@RequestParam("to") String recipient) {
    try {
      System.out.println("Send request to " + recipient);
      CompletableFuture<TxHash> future = transactionService
          .send(AccountAddress.of(recipient), Aer.ONE);
      TxHash txHash = future.get();
      System.out.println("== Confirmed: " + txHash);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}
