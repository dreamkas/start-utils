package ru.dreamkas.utils.concurrent.executors;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.dreamkas.utils.concurrent.ThreadUtils;

public class ScheduledHookExecutor<T> {
    private static final Logger log = LoggerFactory.getLogger(ScheduledHookExecutor.class);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final AtomicBoolean wasHook = new AtomicBoolean();
    private final SynchronizedGate gate = new SynchronizedGate();
    private Callable<T> callable;
    private Duration duration;
    private boolean repeatOnError;
    private boolean sleepOnError;
    private boolean sleepOnSuccess;
    private boolean executionError;

    public ScheduledHookExecutor(Duration duration) {
        this.duration = duration;
    }

    public ScheduledHookExecutor<T> hookOnStart(boolean hookOnStart) {
        wasHook.set(hookOnStart);
        return this;
    }

    public ScheduledHookExecutor<T> repeatOnError(boolean repeatOnError) {
        this.repeatOnError = repeatOnError;
        return this;
    }

    public ScheduledHookExecutor<T> sleepOnError(boolean sleepOnError) {
        this.sleepOnError = sleepOnError;
        return this;
    }

    public ScheduledHookExecutor<T> sleepOnSuccess(boolean sleepOnSuccess) {
        this.sleepOnSuccess = sleepOnSuccess;
        return this;
    }

    public void execute(Callable<T> callable) {
        this.callable = callable;
        executorService.execute(this::innerExecute);
    }

    public void hook() {
        wasHook.getAndSet(true);
        gate.provideAccess();
    }

    private void innerExecute() {
        while (!Thread.interrupted()) {
            waitHook();
            try {
                wasHook.getAndSet(false);
                callable.call();
                executionError = false;
            } catch (Exception e) {
                log.error("Execution exception", e);
                executionError = true;
            }
            sleep();
        }
    }

    private void waitHook() {
        if (executionError && repeatOnError) {
            return;
        }
        if (!wasHook.getAndSet(false)) {
            gate.holdThis();
        }
    }

    private void sleep() {
        if ((executionError && sleepOnError) || (!executionError && sleepOnSuccess)) {
            ThreadUtils.sleep(duration.toMillis());
        }
    }

    public ScheduledHookExecutor setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }
}

