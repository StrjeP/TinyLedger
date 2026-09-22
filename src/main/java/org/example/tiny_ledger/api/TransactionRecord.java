package org.example.tiny_ledger.api;

public record TransactionRecord(String time, MovementAction action, Double amount) {
}
