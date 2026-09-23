package org.example.tiny_ledger.service;

import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.api.MovementResponse;
import org.example.tiny_ledger.ledger.TinyLedger;
import org.example.tiny_ledger.ledger.Transaction;
import org.example.tiny_ledger.ledger.TransactionStatus;

import java.time.Instant;
import java.util.Collection;

public class LedgerServiceImpl implements LedgerService {

    private final TinyLedger ledger;

    public LedgerServiceImpl(TinyLedger ledger) {
        this.ledger = ledger;
    }

    public MovementResponse apply(MovementRequest request) {
        var transaction = new Transaction(this.ledger.getTime(), this.ledger.getId(), TransactionStatus.PENDING, request.action(), request.amount());
        var result = ledger.apply(transaction);
        return new MovementResponse(result.action(), result.amount(), result.id(), result.status().success());
    }

    @Override
    public Double getBalance() {
        return ledger.getBalance();
    }

    @Override
    public Collection<Transaction> getTransactions() {
        return ledger.getTransactions();
    }

    @Override
    public Instant getTime() {
        return ledger.getTime();
    }
}
