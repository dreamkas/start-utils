package ru.dreamkas.utils.time;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StopTimerTest {
    private static final Duration N0 = Duration.ZERO;
    private static final Duration N1 = Duration.ofNanos(1);
    StopTimer S;

    @BeforeEach
    void setup() {
        StopTimer.timeTestSupplier = new AtomicLong(0);
        S = new StopTimer();
    }

    @Test
    @DisplayName("Новый таймер имеет нулевое прошедшее время")
    void zeroElapsedTimeOnInit() {
        assertEquals(N0, S.getElapsed());
    }

    @Test
    @DisplayName("Прошедшее время соответствует фактически прошедшему времени")
    void elapsedTimeFitToRealElapsedTime() {
        systemTime(N1);
        assertEquals(N1, S.getElapsed());
    }

    @Test
    @DisplayName("Перезапуск сбрасывает прошедшее время и начинает его заново")
    void restartResetElapsedTime() {
        systemTime(N1);
        S.restart();
        assertEquals(N0, S.getElapsed());

        systemTime(N1.plus(N1));
        assertEquals(N1, S.getElapsed());
    }

    @Test
    @DisplayName("При получении прошедшее времени в виде строки оно форматируется как HH:mm:ss.SSS")
    void elapsedTimeAsString() {
        Duration systemTime = Duration.ofHours(2).plusMinutes(12).plusSeconds(3).plusMillis(75);
        systemTime(systemTime);

        assertEquals("02:12:03.075", S.getElapsedTimeAsString());
    }

    @Test
    @DisplayName("При получении прошедшего времени в виде строки с перезапуском оно форматируется как H:mm:ss.SSS и отсчет времени начинается заново")
    void elapsedTimeAsStringAndRestart() {
        Duration systemTime = Duration.ofHours(12).plusMinutes(2).plusSeconds(3).plusMillis(75);
        systemTime(systemTime);

        String actualString = S.getElapsedTimeAsStringAndRestart();
        assertEquals("12:02:03.075", actualString);
        assertEquals(N0, S.getElapsed());
    }

    private void systemTime(Duration delta) {
        StopTimer.timeTestSupplier.set(delta.toNanos());
    }
}