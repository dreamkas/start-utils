package ru.dreamkas.utils.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NowTest {
   private ZoneId zone = ZoneId.of("Etc/GMT-10");
   private LocalDate date = LocalDate.of(2016, Month.JANUARY, 1);
   private LocalTime time = LocalTime.of(12, 13, 14);

    private void setUp(Instant instant) {
        Clock clock = Clock.fixed(instant, zone);
        ClockProvider.setClock(clock);
    }

    @AfterAll
    static void reset() {
        ClockProvider.reset();
    }

    @Test
    void zonedDateTime() {
        ZonedDateTime expected = ZonedDateTime.of(date, time, zone);
        setUp(expected.toInstant());

        assertEquals(expected, Now.zonedDateTime());
    }

    @Test
    void localDateTime() {
        LocalDateTime expected = LocalDateTime.of(date, time);
        setUp(expected.atZone(zone).toInstant());

        assertEquals(expected, Now.localDateTime());
    }

    @Test
    void localDate() {
        LocalDate expected = date;
        setUp(expected.atTime(time).atZone(zone).toInstant());
        assertEquals(expected, Now.localDate());
    }

    @Test
    void localTime() {
        LocalTime expected = time;
        setUp(expected.atDate(date).atZone(zone).toInstant());

        assertEquals(expected, Now.localTime());
    }

    @Test
    void date() {
        ZonedDateTime expected = ZonedDateTime.of(date, time, zone);
        setUp(expected.toInstant());

        assertEquals(expected.toInstant().toEpochMilli(), Now.date().getTime());
    }
}