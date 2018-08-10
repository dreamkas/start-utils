package ru.dreamkas.utils.time;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Таймер для замеров затраченного времени на выполнение операции
 * <pre>
 * StopTimer stop = new StopTimer();
 * // long calculation
 * log.info("Calculation complete in {}", stop.getElapsedTimeAsStringAndRestart());
 *
 * // long calculation 2
 * log.info("Calculation 2 complete in {}", stop.getElapsedTimeAsString());
 * </pre>
 * <p>
 * В качестве таймера обратного отсчета следует использовать {@link Timer}
 */
public class StopTimer {
    static AtomicLong timeTestSupplier;
    private long startTime;

    public StopTimer() {
        restart();
    }

    /**
     * Перезапускает таймер для начала нового замера
     */
    public StopTimer restart() {
        startTime = getCurrentTime();
        return this;
    }

    private long getCurrentTime() {
        return timeTestSupplier != null ? timeTestSupplier.get() : System.nanoTime();
    }

    long getElapsedNanos() {
        return getCurrentTime() - startTime;
    }

    public Duration getElapsed() {
        return Duration.ofNanos(getElapsedNanos());
    }

    /**
     * Возвращает прошедшее время в формате H:mm:ss.SSS и перезапускает таймер
     */
    public String getElapsedTimeAsStringAndRestart() {
        String s = getElapsedTimeAsString();
        restart();
        return s;
    }

    /**
     * Возвращает прошедшее время в формате H:mm:ss.SSS
     */
    public String getElapsedTimeAsString() {
        return DurationFormatUtils.formatDurationHMS(getElapsed().toMillis());
    }
}