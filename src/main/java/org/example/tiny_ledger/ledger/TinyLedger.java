package org.example.tiny_ledger.ledger;

import org.jspecify.annotations.NonNull;

import java.time.Instant;
import java.util.*;

public interface TinyLedger {

    Instant getTime();

    long getId();

    Transaction apply(@NonNull Transaction transaction);

    Double getBalance();

    Collection<Transaction> getTransactions();

    default boolean invalidTransaction(Transaction transaction) {
        if (transaction.time() == null ||
                transaction.status() == null ||
                transaction.status() != TransactionStatus.PENDING ||
                transaction.action() == null ||
                transaction.amount() == null ||
                transaction.amount() <= 0) {
            return true;
        }
        return false;
    }
}
