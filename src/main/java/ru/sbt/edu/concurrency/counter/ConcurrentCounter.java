package ru.sbt.edu.concurrency.counter;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCounter implements Counter {
    private final String DEFAULT_KEY = "key";
    private final ConcurrentHashMap<String, Long> countByKey = new ConcurrentHashMap<>();

    public ConcurrentCounter() {
        countByKey.put(DEFAULT_KEY, 0L);
    }

    @Override
    public void increment() {
        countByKey.compute(DEFAULT_KEY, (key, value) -> value + 1);
    }

    @Override
    public long getValue() {
        return countByKey.get(DEFAULT_KEY);
    }
}
