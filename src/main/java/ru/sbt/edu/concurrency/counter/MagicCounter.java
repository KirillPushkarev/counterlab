package ru.sbt.edu.concurrency.counter;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class MagicCounter implements Counter {
    private final long[] values;
    private volatile boolean syncer;

    public MagicCounter(int threadCount) {
        this.values = new long[threadCount];
    }

    @Override
    public void increment() {
        int i = me();
        values[i]++;

        syncer = syncer;
    }

    @Override
    public long getValue() {
        boolean s = syncer;

        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return sum;
    }
}
