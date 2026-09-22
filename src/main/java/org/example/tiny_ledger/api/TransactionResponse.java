package org.example.tiny_ledger.api;

import java.util.List;

public record TransactionResponse(String time, List<TransactionRecord> transactions) {
}
