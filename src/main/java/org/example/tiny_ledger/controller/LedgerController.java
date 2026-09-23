package org.example.tiny_ledger.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiny_ledger.api.*;
import org.example.tiny_ledger.service.LedgerService;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
public class LedgerController {
    private final LedgerService service;

    public LedgerController(LedgerService service) {
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

    @GetMapping("/balance")
    public BalanceResponse getBalance() {
        return new BalanceResponse(InstantFormatter.format(service.getTime()), service.getBalance());
    }

    @GetMapping("/transactions")
    public TransactionResponse getTransactions() {
        var transactionRecords = service.getTransactions().stream()
                .map(t -> new TransactionRecord(InstantFormatter.format(t.time()), t.action(), t.amount())).toList();
        return new TransactionResponse(InstantFormatter.format(service.getTime()), transactionRecords);
    }


}
