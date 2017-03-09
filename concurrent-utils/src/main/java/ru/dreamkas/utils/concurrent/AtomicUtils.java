package ru.dreamkas.utils.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicUtils {

    public static int xorAndGetPrevious(AtomicInteger atomic) {
        int prev, next;
        do {
            prev = atomic.get();
            next = prev ^ 1;
        } while (!atomic.compareAndSet(prev, next));
        return prev;
    }

    public static <T> void set(AtomicReference<T> reference, T newValue) {
        T v;
        do {
            v = reference.get();
        } while (!reference.compareAndSet(v, newValue));
    }

    public static void set(AtomicBoolean reference, boolean newValue) {
        boolean v;
        do {
            v = reference.get();
        } while (!reference.compareAndSet(v, newValue));
    }

}
