package org.example.tiny_ledger.service;

import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.api.MovementResponse;

public interface MoneyMovementService {
    MovementResponse apply(MovementRequest request);
}
