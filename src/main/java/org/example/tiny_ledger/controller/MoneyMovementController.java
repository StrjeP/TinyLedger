package org.example.tiny_ledger.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.api.MovementResponse;
import org.example.tiny_ledger.service.MoneyMovementService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MoneyMovementController {

    private final MoneyMovementService service;

    public MoneyMovementController(MoneyMovementService service) {
        this.service = service;
    }

    @PostMapping("/money")
    public MovementResponse movement(@RequestBody MovementRequest request) {
        try {
            return service.apply(request);
        } catch (Exception e) {
            log.info("Problem processing request: {}", request, e);
            return new MovementResponse(request.action(), request.amount(), -1L, false);
        }
    }


}
