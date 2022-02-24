package ru.sbt.edu.concurrency.counter;

public class MutexCounter implements Counter {
    private volatile long value = 0;

    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public long getValue() {
        return value;
    }
}
