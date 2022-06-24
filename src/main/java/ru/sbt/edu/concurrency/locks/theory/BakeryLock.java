package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class BakeryLock implements ILock {
    private final AtomicBoolean[] flags;
    private final AtomicInteger[] labels;

    public BakeryLock(int maxThreads) {
        flags = new AtomicBoolean[maxThreads];
        labels = new AtomicInteger[maxThreads];
        for (int i = 0; i < maxThreads; i++) {
            flags[i] = new AtomicBoolean(false);
            labels[i] = new AtomicInteger(0);
        }
    }

    @Override
    public void lock() {
        int threadId = me();
        flags[threadId].set(true);
        int nextLabel = Arrays.stream(labels)
                .max(Comparator.comparingInt(AtomicInteger::get))
                .orElseThrow()
                .get() + 1;
        labels[threadId].set(nextLabel);

        while (true) {
            boolean threadAheadExists = IntStream.range(0, labels.length)
                    .anyMatch(i -> flags[i].get() && (labels[i].get() < labels[threadId].get() || (labels[i].get() == labels[threadId].get() && i < threadId)));
            if (!threadAheadExists) {
                break;
            }
        }
    }

    @Override
    public void unlock() {
        int threadId = me();
        flags[threadId].set(false);
    }
}
