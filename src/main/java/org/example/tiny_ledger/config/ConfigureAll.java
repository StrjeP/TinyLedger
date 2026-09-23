package org.example.tiny_ledger.config;

import org.example.tiny_ledger.ledger.TinyLedger;
import org.example.tiny_ledger.ledger.TinyLedgerInMemory;
import org.example.tiny_ledger.service.LedgerService;
import org.example.tiny_ledger.service.LedgerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ConfigureAll {

    @Bean
    public Clock getClock() {
        return Clock.systemUTC();
    }

    @Bean
    public TinyLedger getTinyLedger(Clock clock) {
        return new TinyLedgerInMemory(clock);
    }

    @Bean
    public LedgerService getMoneyMovementService(TinyLedger tinyLedger) {
        return new LedgerServiceImpl(tinyLedger);
    }

}
