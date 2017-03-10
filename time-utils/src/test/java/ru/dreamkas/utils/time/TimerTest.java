package ru.dreamkas.utils.time;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

class TimerTest {
    private static final Duration N0 = Duration.ZERO;
    private static final Duration N1 = Duration.ofNanos(1);
    private static final Duration N2 = Duration.ofNanos(2);
    private static final Duration N3 = Duration.ofNanos(3);
    private Timer T;
    private StopTimer stop;
    private AtomicReference<Duration> elapsed = new AtomicReference<>(N0);

    @BeforeEach
    public void setup() {
        stop = Mockito.mock(StopTimer.class);
        Mockito.when(stop.getElapsedNanos()).thenAnswer((m) -> elapsed.get().toNanos());
        Mockito.when(stop.getElapsed()).thenAnswer((m) -> elapsed.get());

        elapsed(N0);
    }

    @Test
    @DisplayName("Новый таймер в момент создания еще не истек")
    void newTimerIsNotExpired() {
        T = of(N2);
        assertNotExpired();
    }

    @Test
    @DisplayName("Таймер не истек, если прошедшее время меньше времени таймера")
    void timerNotExpiredOnLesserNanos() {
        T = of(N2);
        elapsed(N2.minus(N1));
        assertNotExpired();
    }

    @Test
    @DisplayName("Таймер истек, если прошедшее время равно времени таймера")
    void timerExpiredOnEqualNanos() {
        T = of(N2);
        elapsed(N2);
        assertExpired();
    }

    @Test
    @DisplayName("Таймер истек, если прошедшее время больше времени таймера")
    void timerExpiredOnGreaterNanos() {
        T = of(N2);
        elapsed(N2.plus(N1));
        assertExpired();
    }

    @Test
    @DisplayName("Перезапуск таймера приводит к перезапуску StopTimer")
    void restartRestartsStopTimer() {
        T = of(N1);
        T.restart();
        T.restart(N2);
        Mockito.verify(stop, VerificationModeFactory.times(2)).restart();
    }

    @Test
    @DisplayName("Перезапуск таймера без параметров не меняет его время")
    void restartWithoutParamNotChangeTime() {
        T = of(N1);
        elapsed(N1);

        T.restart();
        assertExpired();
    }

    @Test
    @DisplayName("Перезапуск таймера с параметром меняет его время")
    void restartWithParamChangesTime() {
        T = of(N1);
        elapsed(N1);

        T.restart(N2);
        assertNotExpired();

        elapsed(N2);
        assertExpired();
    }

    @Test
    @DisplayName("Новый истекший таймер в момент создания уже истек")
    void newExpiredTimerIsExpired() {
        T = expired(N2);
        assertExpired();
    }

    @Test
    @DisplayName("Новый истекший таймер после перезапуска перестает быть истекшим")
    void newExpiredTimerNotExpiredAfterRestart() {
        T = expired(N2);

        T.restart();
        assertNotExpired();
    }

    @Test
    @DisplayName("Новый таймер с нулевым временем после перезапуска с новым временем перестает быть истекшим")
    void newZeroTimerNotExpiredAfterRestartWithNewTime() {
        T = of(N0);
        T.restart(N1);
        assertNotExpired();
    }

    @Test
    @DisplayName("При получении прошедшего времени оно соответствует прошедшему времени StopTimer")
    void elapsedTimeEqualsToStopTimer() {
        T = of(N2);
        elapsed(N1);

        Duration elapsedFromTimer = T.getElapsed();
        Assertions.assertEquals(N1, elapsedFromTimer);
    }

    @Test
    @DisplayName("Оставшееся время равно разнице времени таймера и прошедшего времени")
    void remainTimeEqualsToStopTimer() {
        T = of(N3);
        elapsed(N1);

        Duration remainFromTimer = T.getRemain();
        Assertions.assertEquals(N3.minus(N1), remainFromTimer);
    }

    private void assertNotExpired() {
        Assertions.assertTrue(T.isNotExpired(), "Таймер истек (не должен был истечь)");
        Assertions.assertFalse(T.isExpired(), "Таймер истек (не должен был истечь)");
    }

    private void assertExpired() {
        Assertions.assertTrue(T.isExpired(), "Таймер не истек (должен был истечь)");
        Assertions.assertFalse(T.isNotExpired(), "Таймер не истек (должен был истечь)");
    }

    private void elapsed(Duration delta) {
        elapsed.set(delta);
    }

    private Timer of(Duration d) {
        return new Timer(d, stop, false);
    }

    private Timer expired(Duration d) {
        return new Timer(d, stop, true);
    }
}