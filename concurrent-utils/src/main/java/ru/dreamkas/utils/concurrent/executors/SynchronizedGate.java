package ru.dreamkas.utils.concurrent.executors;

public class SynchronizedGate {
    private final Object mutex = new Object();

    public void holdThis() {
        try {
            synchronized (mutex) {
                mutex.wait();
            }
        } catch (InterruptedException ignored) {
        }
    }

    public void provideAccess() {
        synchronized (mutex) {
            mutex.notify();
        }
    }
}
