package org.example.tiny_ledger.service;

import org.example.tiny_ledger.api.MovementAction;
import org.example.tiny_ledger.api.MovementRequest;
import org.example.tiny_ledger.ledger.TinyLedger;
import org.example.tiny_ledger.ledger.Transaction;
import org.example.tiny_ledger.ledger.TransactionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoneyMovementServiceImplTest {
    private static Instant fixedInstant = Instant.parse("2026-09-22T18:32:12Z");
    private static Long id = 12345L;

    @Mock
    TinyLedger ledger;

    @Mock
    Transaction mockTransaction;

    @Test
    @DisplayName("happy path")
    void happyPathTest() {
        var sut = new MoneyMovementServiceImpl(ledger);
        var action = MovementAction.DEPOSIT;
        var amount = 123.12;
        var testRequest = new MovementRequest(action, amount);

        when(ledger.getTime()).thenReturn(fixedInstant);
        when(ledger.getId()).thenReturn(id);
        when(ledger.apply(any(Transaction.class))).thenReturn(mockTransaction);
        when(mockTransaction.status()).thenReturn(TransactionStatus.SUCCESS);

        sut.apply(testRequest);

        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(ledger).apply(transactionCaptor.capture());
        var capturedTransaction = transactionCaptor.getValue();

        assertEquals(fixedInstant, capturedTransaction.time());
        assertEquals(id, capturedTransaction.id());
        assertEquals(action, capturedTransaction.action());
        assertEquals(amount, capturedTransaction.amount());
    }
}