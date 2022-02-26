package ru.sbt.edu.concurrency.locks.theory;

import ru.sbt.edu.concurrency.locks.ILock;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;

public class LockTwo implements ILock {
    private volatile int victim;

    @Override
    public void lock() {
        int i = me();

        victim = i;
        while (victim == i) {
        }
    }

    @Override
    public void unlock() {
        victim = me();
    }
}
