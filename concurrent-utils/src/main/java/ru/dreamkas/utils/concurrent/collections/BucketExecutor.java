package ru.dreamkas.utils.concurrent.collections;

import java.util.List;

@FunctionalInterface
public interface BucketExecutor<T> {
    void execute(List<T> t) throws ConcurrentAutoFlushingException;
}
