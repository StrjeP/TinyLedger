package org.example.tiny_ledger.controller;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InstantFormatterTest {
    private static Instant fixedInstant = Instant.parse("2026-09-22T18:32:12Z");

    @Test
    void formatInstantTest() {
        var expected = "2026-09-22 18:32:12";
        assertEquals(expected, InstantFormatter.format(fixedInstant));
    }
}