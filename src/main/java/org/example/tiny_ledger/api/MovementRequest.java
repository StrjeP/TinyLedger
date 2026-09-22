package org.example.tiny_ledger.api;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(toBuilder = true)
public record MovementRequest(MovementAction action, Double amount) {
}
