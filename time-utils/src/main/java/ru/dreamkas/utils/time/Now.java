package ru.dreamkas.utils.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Возвращает текущее времени в разных типах, вычисляемое с помощью управляемового {@link ClockProvider}
 * <p>
 * Последовательное использование в коде позволяет управлять временем, что необходимо для некоторых тестов.
 * <p>
 * Пример использования:
 * <pre>
 *     LocalDate nowDate = Now.localDate();
 *     LocalDateTime nowDateTime = Now.localDateTime();
 * </pre>
 */
public class Now {
    public static ZonedDateTime zonedDateTime() {
        return ZonedDateTime.now(ClockProvider.get());
    }

    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(ClockProvider.get());
    }

    public static LocalDate localDate() {
        return LocalDate.now(ClockProvider.get());
    }

    public static LocalTime localTime() {
        return LocalTime.now(ClockProvider.get());
    }

    @SuppressWarnings("unused")
    public static Instant instant() {
        return Instant.now(ClockProvider.get());
    }

    /**
     * Возвращает экземпляр {@link Date} с текущим временем на базе {@link ClockProvider}
     *
     * @deprecated не использовать {@link Date} в новом коде, а старый код последовтательно переписывать на Java Time API: {@link ZonedDateTime}, {@link LocalDateTime},  {@link LocalDate}, {@link LocalTime}
     */
    @Deprecated
    public static Date date() {
        return DateConverters.toDate(zonedDateTime());
    }

    /**
     * Возвращает номер дня недели в формате {@link java.util.Calendar}<br>
     *      <code>SUNDAY = 1, </code><br>
     *      <code>MONDAY = 2, </code><br>
     *      <code>TUESDAY = 3, </code><br>
     *      <code>WEDNESDAY = 4, </code><br>
     *      <code>THURSDAY = 5, </code><br>
     *      <code>FRIDAY = 6, </code><br>
     *      <code>SATURDAY = 7</code>
     *
     * @return int
     */
    @SuppressWarnings("unused")
    public static int getDayOfWeek() {
        int dow = localDate().getDayOfWeek().getValue() + 1;
        return dow > 7 ? 1 : dow;
    }
}
