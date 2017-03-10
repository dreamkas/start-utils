package ru.dreamkas.utils.concurrent.collections;

import java.util.Collection;
import java.util.Collections;

public class ConcurrentAutoFlushingException extends Exception {
    private Collection<?> expiredItems = null;

    public ConcurrentAutoFlushingException(String message) {
        super(message);
    }

    public ConcurrentAutoFlushingException(Throwable cause) {
        super(cause);
    }

    private ConcurrentAutoFlushingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentAutoFlushingException(String message, Collection<?> expiredItems, Throwable cause) {
        this(message, cause);
        this.expiredItems = expiredItems;
    }

    Collection<?> getExpiredItems() {
        return expiredItems != null ? expiredItems : Collections.emptySet();
    }

}
