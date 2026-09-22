package org.example.tiny_ledger.ledger;

import org.example.tiny_ledger.api.MovementAction;

import java.time.Instant;

public record Transaction(Instant time, long id, TransactionStatus status, MovementAction action, Double amount) {
}
