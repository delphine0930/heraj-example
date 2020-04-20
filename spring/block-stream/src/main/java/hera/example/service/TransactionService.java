package hera.example.service;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.TxHash;
import java.util.concurrent.CompletableFuture;

public interface TransactionService {

  CompletableFuture<TxHash> send(AccountAddress recipient, Aer amount);

}
