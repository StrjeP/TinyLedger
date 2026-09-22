package org.example.tiny_ledger.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.tiny_ledger.api.BalanceResponse;
import org.example.tiny_ledger.api.TransactionRecord;
import org.example.tiny_ledger.api.TransactionResponse;
import org.example.tiny_ledger.ledger.TinyLedger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@RestController
@Slf4j
public class LedgerController {
    private final TinyLedger ledger;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);

    public LedgerController(TinyLedger ledger) {
        this.ledger = ledger;
    }

    @GetMapping("/balance")
    public BalanceResponse getBalance() {
        return new BalanceResponse(formatter.format(ledger.getTime()), ledger.getBalance());
    }

    @GetMapping("/transactions")
    public TransactionResponse getTransactions() {
        var transactionRecords = ledger.getTransactions().stream()
                .map(t -> new TransactionRecord(formatter.format(t.time()), t.action(), t.amount())).toList();
        return new TransactionResponse(formatter.format(ledger.getTime()), transactionRecords);
    }

}
