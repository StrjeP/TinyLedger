package org.example.tiny_ledger.api;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public record BalanceResponse(String time, Double amount) {
}
