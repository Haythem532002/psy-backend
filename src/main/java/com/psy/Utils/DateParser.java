package com.psy.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateParser {

    public static LocalDateTime parseDateString(String dateString) {
        try {
            // Parse the string as an Instant (UTC)
            Instant instant = Instant.parse(dateString);

            // Convert to local date time in system's default timezone
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            // Log for troubleshooting
            System.out.println("📅 Parsed LocalDateTime: " + localDateTime);

            return localDateTime;
        } catch (Exception e) {
            System.err.println("❌ Failed to parse date string: " + dateString);
            e.printStackTrace();
            throw e;
        }
    }
}
