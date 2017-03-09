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
}
