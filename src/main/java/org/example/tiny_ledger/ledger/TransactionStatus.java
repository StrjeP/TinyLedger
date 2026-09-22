package org.example.tiny_ledger.ledger;

public enum TransactionStatus {
    PENDING(null), SUCCESS(true), FAILURE(false);

    private final Boolean success;

    TransactionStatus(Boolean success) {
        this.success = success;
    }

    public Boolean success(){
        return success;
    }
}
