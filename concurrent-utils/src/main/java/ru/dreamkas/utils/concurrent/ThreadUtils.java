package ru.dreamkas.utils.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    public static void sleep(long delay) {
        sleep(delay, TimeUnit.MILLISECONDS);
    }

    public static void sleep(long delay, TimeUnit unit) {
        try {
            if (delay > 0) {
                Thread.sleep(unit.toMillis(delay));
            }
        } catch (InterruptedException ignored) {
        }
    }
}
