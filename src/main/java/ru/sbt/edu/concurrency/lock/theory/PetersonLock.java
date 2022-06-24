package ru.sbt.edu.concurrency.lock.theory;

import ru.sbt.edu.concurrency.lock.ILock;

import static ru.sbt.edu.concurrency.util.TwoThreadIds.me;
import static ru.sbt.edu.concurrency.util.TwoThreadIds.not;

public class PetersonLock implements ILock {
    private final boolean[] flag = new boolean[2];
    private volatile int victim;

    @Override
    public void lock() {
        int i = me();
        int j = not(i);

        flag[i] = true;
        victim = i;
        while (flag[j] && victim == i) {
        }
    }

    @Override
    public void unlock() {
        int i = me();
        flag[i] = false;
    }
}
