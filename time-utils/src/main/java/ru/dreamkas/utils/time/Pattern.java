package ru.dreamkas.utils.time;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

/**
 * Шаблоны дат и времени для форматирования/парсинга.
 * <p>
 * Позволяет снизить число ошибок при
 * форматировании даты (например, использования YY вместо yy, которые в конце декабря могут возвращать следующий год)
 * <p>
 * Пример использования:
 * <pre>
 *     LocalDate date = ...;
 *     String formatted date.format(Pattern.DDMMYY.formatter());
 * </pre>
 */
public enum Pattern {
    /**
     * ДДММГГ
     */
    DDMMYY("ddMMyy"),
    /**
     * ДДММГГГГ
     */
    DDMMYYYY("ddMMyyyy"),
    /**
     * ДД.ММ.ГГГГ
     */
    DDMMYYYY_dots("dd.MM.yyyy"),
    /**
     * ДД.ММ.ГГГГ ЧЧ:мм:сс
     */
    DDMMYYYY_HHmmss_dots_colon("dd.MM.yyyy HH:mm:ss"),
    /**
     * ДД.ММ.ГГГГ Ч:мм:сс
     */
    DDMMYYYY_Hmmss_dots_colon("dd.MM.yyyy H:mm:ss"),
    /**
     * ДД.ММ.ГГГГ ЧЧ:мм
     */
    DDMMYYYY_HHmm_dots_colon("dd.MM.yyyy HH:mm"),
    /**
     * ЧЧ:мм:сс
     */
    HHmmss_colon("HH:mm:ss"),
    /**
     * ЧЧ:мм
     */
    HHmm_colon("HH:mm"),
    /**
     * Ч:мм:сс
     */
    Hmmss_colon("H:mm:ss"),
    /**
     * Ч:мм
     */
    Hmm_colon("H:mm"),
    /**
     * ЧЧммсс
     */
    HHmmss("HHmmss"),
    /**
     * ДДММГГЧЧммсс
     */
    DDMMYYHHmmss("ddMMyyHHmmss"),
    /**
     * ДДММГГЧЧмм
     */
    DDMMYYHHmm("ddMMyyHHmm"),
    /**
     * ГГГГ-ММ-ДД ЧЧ:мм:сс.ССС Z
     */
    YYYYMMDD_HHmmssSSS_Z_dash_colon("yyyy-MM-dd HH:mm:ss.SSS Z"),
    /**
     * ГГГГ-ММ-ДД ЧЧ:мм:сс Z
     */
    YYYYMMDD_HHmmss_Z_dash_colon("yyyy-MM-dd HH:mm:ss Z"),
    /**
     * ГГГГ-ММ-ДД ЧЧ:мм:сс
     */
    YYYYMMDD_HHmmss_dash_colon("yyyy-MM-dd HH:mm:ss"),
    /**
     * ГГГГ-ММ-ДД ЧЧ:мм
     */
    YYYYMMDD_HHmm_dash_colon("yyyy-MM-dd HH:mm"),
    /**
     * ДД-ММ-ГГГГ ЧЧ:мм:сс
     */
    DDMMYYYY_HHmmss_dash_colon("dd-MM-yyyy HH:mm:ss"),
    /**
     * ДД-ММ-ГГГГ
     */
    DDMMYYYY_dash("dd-MM-yyyy"),
    /**
     * ГГГГ-ММ-ДД
     */
    YYYYMMDD_dash("yyyy-MM-dd"),
    /**
     * ГГГГ.ММ.ДД-ЧЧ:мм:сс
     */
    YYYYMMDD_HHmmss_dots_dash_colons("yyyy.MM.dd-HH:mm:ss"),
    /**
     * ГГГГММДД
     */
    YYYYMMDD("yyyyMMdd"),
    /**
     * ГГГГММДДЧЧммсс
     */
    YYYYMMDDHHmmss("yyyyMMddHHmmss"),
    /**
     * ГГММДДЧЧмм
     */
    YYMMDDHHMM("yyMMddHHmm");
    private static Map<Pattern, DateTimeFormatter> cached = new EnumMap<>(Pattern.class);
    final private String pattern;

    Pattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    /**
     * Возвращает форматтер для шаблона из кеша или создает его
     * <p>
     * Используемый {@link DateTimeFormatter} потокобезопасен, поэтому экземпляры могут переиспользоваться
     */
    public DateTimeFormatter formatter() {
        return cached.computeIfAbsent(this, s -> DateTimeFormatter.ofPattern(this.getPattern()));
    }

    /**
     * Создает форматтер для шаблона
     * <p>
     * Используемый {@link SimpleDateFormat} НЕ-потокобезопасен, поэтому возвращается всегда новый экземпляр
     *
     * @deprecated @deprecated не использовать {@link Date} и этот форматтер для него в новом коде, а старый код последовтательно переписывать на Java
     * Time API: {@link ZonedDateTime}, {@link LocalDateTime},  {@link LocalDate}, {@link LocalTime}
     */
    @Deprecated
    public SimpleDateFormat toSimpleDateFormat() {
        return new SimpleDateFormat(this.getPattern());
    }

}
