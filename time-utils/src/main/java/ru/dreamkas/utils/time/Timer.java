package ru.dreamkas.utils.time;

import java.time.Duration;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Таймер обратного отсчета для циклов ожидания наступления события в течение заданного отрезка времени
 * <p>
 * Пример использования:
 * <p>
 * <pre>
 * Timer timer = Timer.of(Duration.ofSeconds(5));
 * while (timer.isNotExpired()) {
 *     // ...
 *     if (needWaitAgain()) {
 *         timer.restart();
 *     }
 *     if (needWaitEvenMoreTime()) {
 *         timer.restart(Duration.ofMinute(1));
 *     }
 *     // ...
 * }
 * </pre>
 * <p>
 * Для замеров затраченного времени следует использовать {@link StopTimer}
 */
public class Timer {
    private final StopTimer stopTimer;
    private long timeout;
    private boolean initiallyExpired;

    /**
     * Конструктор для тестов
     */
    Timer(Duration timeout, StopTimer stopTimer, boolean initiallyExpired) {
        this.timeout = timeout.toNanos();
        this.stopTimer = stopTimer;
        this.initiallyExpired = initiallyExpired;
    }

    /**
     * Новый таймер с заданной длительностью
     */
    public static Timer of(Duration timeout) {
        return new Timer(timeout, new StopTimer(), false);
    }

    /**
     * Новый таймер с заданной длительностью в мс
     *
     * @Deprecated оставлен для своместимости с некоторым старым кодом, где очень много констант таймаутов в мс. В новом коде рекомендутся
     * использовать @{@link Duration} для описания таймаутов и и соответствующий метод таймера {@link Timer#of(Duration)}
     */
    @Deprecated
    public static Timer ofMillis(long timeoutMillis) {
        return of(Duration.ofMillis(timeoutMillis));
    }

    /**
     * Новый истекший таймер с заданной длительностью
     */
    public static Timer expired(Duration timeout) {
        return new Timer(timeout, new StopTimer(), true);
    }

    /**
     * Перезапуск таймера с заданной длительностью
     */
    public Timer restart(Duration timeout) {
        this.timeout = timeout.toNanos();
        return restart();
    }

    /**
     * Перезапуск таймера с начальной длительностью
     */
    public Timer restart() {
        initiallyExpired = false;
        stopTimer.restart();
        return this;
    }

    /**
     * Проверяет, что таймер еще не истек
     */
    public boolean isNotExpired() {
        return !isExpired();
    }

    /**
     * Проверяет, что таймер уже не истек
     */
    public boolean isExpired() {
        return initiallyExpired || getElapsedNanos() >= timeout;
    }

    /**
     * Возвращает оставшееся время
     */
    public Duration getRemain() {
        return Duration.ofNanos(getRemainNanos());
    }

    private long getRemainNanos() {
        return timeout - getElapsedNanos();
    }

    private long getElapsedNanos() {
        return stopTimer.getElapsedNanos();
    }

    /**
     * Возвращает прошедшее время
     */
    public Duration getElapsed() {
        return stopTimer.getElapsed();
    }

    /**
     * Возвращает прошедшее время в формате H:mm:ss.SSS
     */
    public String getElapsedTimeAsString() {
        return stopTimer.getElapsedTimeAsString();
    }

    /**
     * Возвращает оставшееся время в формате H:mm:ss.SSS
     */
    public String getRemainTimeAsString() {
        return DurationFormatUtils.formatDurationHMS(getRemain().toMillis());
    }
}