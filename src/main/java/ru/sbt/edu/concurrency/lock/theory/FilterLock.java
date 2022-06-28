package ru.sbt.edu.concurrency.lock.theory;

import ru.sbt.edu.concurrency.lock.ILock;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class FilterLock implements ILock {
    private final int maxThreads;
    private final AtomicInteger[] levelByThread;
    private final AtomicInteger[] victimByLevel;

    public FilterLock(int maxThreads) {
        this.maxThreads = maxThreads;
        levelByThread = new AtomicInteger[maxThreads];
        victimByLevel = new AtomicInteger[maxThreads];
        for (int i = 0; i < maxThreads; i++) {
            levelByThread[i] = new AtomicInteger();
            victimByLevel[i] = new AtomicInteger();
        }
    }

    @Override
    public void lock() {
        int threadId = me();

        for (int i = 1; i < maxThreads; i++) {
            levelByThread[threadId].set(i);
            victimByLevel[i].set(threadId);

            for (int k = 0; k < maxThreads; k++) {
                while (k != threadId && levelByThread[k].get() >= i && victimByLevel[i].get() == threadId) {
                }
            }
        }
    }

    @Override
    public void unlock() {
        int threadId = me();
        levelByThread[threadId].set(0);
    }
}
