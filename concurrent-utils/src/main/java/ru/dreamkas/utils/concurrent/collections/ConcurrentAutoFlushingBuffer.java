package ru.dreamkas.utils.concurrent.collections;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentAutoFlushingBuffer<T> {
    private static final Logger log = LoggerFactory.getLogger(ConcurrentAutoFlushingBuffer.class);
    private final BucketExecutor<T> dataExecutor;
    private final ConcurrentSkipListSet<T> bucket;
    private final ScheduledExecutorService executor;
    private Integer packetSize = null;

    public ConcurrentAutoFlushingBuffer(int flushPeriod, Integer packetSize, Comparator<T> comparator, BucketExecutor<T> dataExecutor) {
        this.packetSize = packetSize;
        this.dataExecutor = dataExecutor;
        bucket = new ConcurrentSkipListSet<>(comparator != null ? comparator : (o1, o2) -> 0);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this::flush, flushPeriod, flushPeriod, TimeUnit.MILLISECONDS);
    }

    public void put(T item) {
        bucket.add(item);
    }

    public void putAll(List<T> items) {
        bucket.addAll(items);
    }

    public void immediatelyFlush() {
        executor.execute(this::flush);
    }

    public void shutdown() {
        executor.shutdown();
    }

    private synchronized void flush() {
        List<T> data = bucket.stream().limit(packetSize == null ? bucket.size() : packetSize).collect(Collectors.toList());
        try {
            if (data.size() > 0) {
                dataExecutor.execute(data);
                bucket.removeAll(data);
            }
        } catch (ConcurrentAutoFlushingException e) {
            bucket.removeAll(e.getExpiredItems());
            log.error("DROP CASHED DATA FROM BUCKET", e);
        }
    }
}
