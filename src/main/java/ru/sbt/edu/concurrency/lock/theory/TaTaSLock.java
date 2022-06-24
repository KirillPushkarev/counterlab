package ru.sbt.edu.concurrency.lock.theory;

import ru.sbt.edu.concurrency.lock.ILock;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaTaSLock implements ILock {
    private final AtomicBoolean flag;

    public TaTaSLock() {
        flag = new AtomicBoolean(false);
    }

    @Override
    public void lock() {
        while (true) {
            while (flag.get()) {
            }
            if (flag.compareAndSet(false, true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        flag.set(false);
    }
}
