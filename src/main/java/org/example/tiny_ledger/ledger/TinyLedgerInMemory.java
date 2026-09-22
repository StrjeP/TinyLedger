package org.example.tiny_ledger.ledger;

import org.example.tiny_ledger.api.MovementAction;
import org.jspecify.annotations.NonNull;

import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TinyLedgerInMemory implements TinyLedger {
    private Double balance = 0.0;
    private final Clock clock;

    final Map<Long, Transaction> successfulTransactions = new TreeMap<>(Comparator.<Long>comparingLong(i -> i).reversed());

    public TinyLedgerInMemory(Clock clock) {
        this.clock = clock;
    }

    public Instant getTime() {
        return clock.instant();
    }

    public long getId() {
        return clock.millis();
    }

    public Transaction apply(@NonNull Transaction transaction) {
        if (invalidTransaction(transaction)) {
            return new Transaction(transaction.time(), transaction.id(), TransactionStatus.FAILURE, transaction.action(), transaction.amount());
        }
        try {
            if (transaction.action().equals(MovementAction.DEPOSIT)) {
                this.balance += transaction.amount();
            } else {
                this.balance -= transaction.amount();
            }
            var successfulTransaction = new Transaction(transaction.time(), transaction.id(), TransactionStatus.SUCCESS, transaction.action(), transaction.amount());
            this.successfulTransactions.put(transaction.id(), successfulTransaction);
            return successfulTransaction;
        } catch (Exception e) {
            return new Transaction(transaction.time(), transaction.id(), TransactionStatus.FAILURE, transaction.action(), transaction.amount());
        }
    }

    public Double getBalance() {
        return balance;
    }

    public Collection<Transaction> getTransactions() {
        return successfulTransactions.values();
    }
}
