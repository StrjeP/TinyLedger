package org.example.tiny_ledger.service;

import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.api.MovementResponse;
import org.example.tiny_ledger.ledger.TinyLedger;
import org.example.tiny_ledger.ledger.Transaction;
import org.example.tiny_ledger.ledger.TransactionStatus;

public class MoneyMovementServiceImpl implements MoneyMovementService {

    private final TinyLedger ledger;

    public MoneyMovementServiceImpl(TinyLedger ledger) {
        this.ledger = ledger;
    }

    public MovementResponse apply(MovementRequest request) {
        var transaction = new Transaction(this.ledger.getTime(), this.ledger.getId(), TransactionStatus.PENDING, request.action(), request.amount());
        var result = ledger.apply(transaction);
        return new MovementResponse(result.action(), result.amount(), result.id(), result.status().success());
    }
}
