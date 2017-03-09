package ru.dreamkas.utils.time;

import java.time.Clock;
import java.time.ZoneId;
import java.util.TimeZone;

public class ClockProvider {
    private static Clock instance = Clock.systemDefaultZone();

    public static void setClock(Clock clock) {
        instance = clock;
    }

    public static Clock get() {
        return instance;
    }

    public static void reset() {
        instance = Clock.systemDefaultZone();
    }

    public static void setTimeZoneAndReset(ZoneId timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        reset();
    }
}
