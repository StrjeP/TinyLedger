package org.example.tiny_ledger.ledger;

import org.example.tiny_ledger.api.MovementAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TinyLedgerInMemoryInMemoryTest {
    private static Instant fixedInstant = Instant.parse("2026-09-22T18:32:12Z");
    private static ZoneId zone = ZoneId.of("UTC");
    private static Clock testClock = Clock.fixed(fixedInstant, zone);

    @Test
    @DisplayName("Simple test of clock")
    void simpleClockTest() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(fixedInstant, sut.getTime());
    }

    @Test
    @DisplayName("Simple test of id")
    void simpleIdTest() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(fixedInstant.toEpochMilli(), sut.getId());
    }

    @Test
    @DisplayName("Simple default balance is zero")
    void defaultBalanceIsZero() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(0.0, sut.getBalance());
    }

    @Test
    @DisplayName("Simple balance deposit")
    void defaultBalanceDeposit() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(0.0, sut.getBalance());
        var amount = 123.4;
        sut.apply(new Transaction(fixedInstant, fixedInstant.getEpochSecond(), TransactionStatus.PENDING, MovementAction.DEPOSIT, amount));
        assertEquals(amount, sut.getBalance(), 0.0001);
    }

    @Test
    @DisplayName("Simple balance withdraw")
    void defaultBalanceWithdraw() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(0.0, sut.getBalance());
        var amount = 123.4;
        sut.apply(new Transaction(fixedInstant, fixedInstant.getEpochSecond(), TransactionStatus.PENDING, MovementAction.WITHDRAW, amount));
        assertEquals(-amount, sut.getBalance(), 0.0001);
    }

    @DisplayName("Test when problem with Transaction")
    @ParameterizedTest()
    @MethodSource()
    void testInvalidTransaction(Transaction transaction) {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(TransactionStatus.FAILURE, sut.apply(transaction).status());
    }

    private static Stream<Transaction> testInvalidTransaction() {
        var invalidTransactions = List.of(
                new Transaction(null, 1, TransactionStatus.PENDING, MovementAction.DEPOSIT, 123.2),
                new Transaction(fixedInstant, 1, TransactionStatus.SUCCESS, MovementAction.DEPOSIT, 123.2),
                new Transaction(fixedInstant, 1, TransactionStatus.PENDING, null, 123.2),
                new Transaction(fixedInstant, 1, TransactionStatus.PENDING, MovementAction.DEPOSIT, -123.2)
        );
        return invalidTransactions.stream();
    }

    @Test
    @DisplayName("Check the transactions are applied")
    void transactionsApplied() {
        var sut = new TinyLedgerInMemory(testClock);
        assertEquals(0.0, sut.getBalance());

        // should be +2000 -1200 -500 + 123
        // 2000 -> 800 -> 300 - 423
        var transactionD2000 =new Transaction(fixedInstant, 1, TransactionStatus.PENDING, MovementAction.DEPOSIT, 2000.0);
        var transactionW1200 =new Transaction(fixedInstant .plusSeconds(2), 2, TransactionStatus.PENDING, MovementAction.WITHDRAW, 1200.0);
        var transactionW500 =new Transaction(fixedInstant.plusSeconds(3), 3, TransactionStatus.PENDING, MovementAction.WITHDRAW, 500.0);
        var transactionD123 =new Transaction(fixedInstant.plusSeconds(4), 4, TransactionStatus.PENDING, MovementAction.DEPOSIT, 123.0);
        var expected = List.of(transactionD2000, transactionW1200, transactionW500, transactionD123);

        for (var t: expected) {
            sut.apply(t);
        }

        assertEquals(423.0, sut.getBalance());

        var transactions = sut.getTransactions();
        assertEquals(4, transactions.size());

        // Transactions are reverse ordered
        var i = 3;
        for (var t: transactions) {
            verifyTransaction(expected.get(i), t);
            i--;
        }
    }

    private void verifyTransaction(Transaction expected, Transaction result) {
        assertEquals(expected.time(), result.time());
        assertEquals(expected.id(), result.id());
        assertEquals(TransactionStatus.SUCCESS, result.status());
        assertEquals(expected.action(), result.action());
        assertEquals(expected.amount(), result.amount());
    }
}