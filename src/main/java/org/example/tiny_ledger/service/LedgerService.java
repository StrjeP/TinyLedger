package org.example.tiny_ledger.service;

import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.api.MovementResponse;
import org.example.tiny_ledger.ledger.Transaction;

import java.time.Instant;
import java.util.Collection;

public interface LedgerService {
    MovementResponse apply(MovementRequest request);

    Double getBalance();

    Collection<Transaction> getTransactions();

    Instant getTime();
}
