package ru.dreamkas.utils.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Переводит одни типы дат в другие
 */
public class DateConverters {
    public static Date toDate(LocalTime localTime) {
        return localTime != null ? toDate(localTime.atDate(Now.localDate())) : null;
    }

    public static Date toDate(LocalDate localDate) {
        return localDate != null ? toDate(localDate.atStartOfDay()) : null;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime != null ? toDate(localDateTime.atZone(ZoneId.systemDefault())) : null;
    }

    public static Date toDate(ZonedDateTime zonedDateTime) {
        return zonedDateTime != null ? Date.from(zonedDateTime.toInstant()) : null;
    }

    public static LocalTime toLocalTime(Date date) {
        return date != null ? toLocalDateTime(date).toLocalTime() : null;
    }

    public static LocalDate toLocalDate(Date date) {
        return date != null ? toLocalDateTime(date).toLocalDate() : null;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date != null ? LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) : null;
    }

    public static ZonedDateTime toZonedDateTime(Date date) {
        return date != null ? ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()) : null;
    }

    public static LocalDateTime toLocalDateTime(long epochMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(XMLGregorianCalendar date) {
        return date != null ? toLocalDateTime(date.toGregorianCalendar().getTime()) : null;
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime date) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(toDate(date));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate date) {
        return toXMLGregorianCalendar(LocalDateTime.of(date, LocalTime.MIN));
    }


}
