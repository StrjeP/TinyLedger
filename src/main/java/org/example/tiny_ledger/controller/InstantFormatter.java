package org.example.tiny_ledger.controller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantFormatter {
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);

    private InstantFormatter() {
    }

    public static String format(Instant instant) {
        return formatter.format(instant);
    }
}
