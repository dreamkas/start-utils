package ru.dreamkas.utils.time;

import java.time.Clock;
import java.time.ZoneId;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockProviderTest {
    @BeforeEach
    public void before() {
        ClockProvider.reset();
    }

    @AfterAll
    public static void reset() {
        ClockProvider.reset();
    }

    @Test
    void defaultInstanceIsSystemDefaultZone() {
        Clock expected = Clock.systemDefaultZone();
        assertEquals(expected, ClockProvider.get());
    }

    @Test
    void setClock() {
        Clock expected = Clock.tickSeconds(ZoneId.systemDefault());
        ClockProvider.setClock(expected);
        assertEquals(expected, ClockProvider.get());
    }

    @Test
    void resetClock() {
        Clock expected = Clock.systemDefaultZone();
        ClockProvider.setClock(Clock.tickSeconds(ZoneId.systemDefault()));
        ClockProvider.reset();
        assertEquals(expected, ClockProvider.get());
    }
}