package ru.sbt.edu.concurrency.counter;

import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentCounter implements Counter {
    private final AtomicLong value = new AtomicLong(0);

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public long getValue() {
        return value.longValue();
    }
}
