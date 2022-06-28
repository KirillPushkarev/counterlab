package ru.sbt.edu.concurrency.lock;

import org.junit.jupiter.api.Test;
import ru.sbt.edu.concurrency.counter.*;
import ru.sbt.edu.concurrency.lock.theory.BakeryLock;
import ru.sbt.edu.concurrency.lock.theory.FilterLock;
import ru.sbt.edu.concurrency.lock.theory.PetersonLock;
import ru.sbt.edu.concurrency.lock.theory.TaTaSLock;
import ru.sbt.edu.concurrency.util.TwoThreadIds;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ILockTest {
    @Test
    public void testPetersonLock() {
        ILock lock = new PetersonLock();
        Counter counter = new ILockCounter(lock);
        testCounter(counter, 1000, 2);
    }

    @Test
    public void testFilterLock() {
        ILock lock = new FilterLock(4);
        Counter counter = new ILockCounter(lock);
        testCounter(counter, 10000, 4);
    }

    @Test
    public void testBakeryLock() {
        ILock lock = new BakeryLock(4);
        Counter counter = new ILockCounter(lock);
        testCounter(counter, 10000, 4);
    }

    @Test
    public void testTaTaSLock() {
        ILock lock = new TaTaSLock();
        Counter counter = new ILockCounter(lock);
        testCounter(counter, 10000, 4);
    }

    @Test
    public void testNaiveCounter() {
        Counter counter = new SeqCounter();

        testCounter(counter, 1000, 2);
    }

    @Test
    public void testConcurrentCounter() {
        Counter counter = new ConcurrentCounter();

        testCounter(counter, 10000, 4);
    }

    @Test
    public void testMagicCounter() {
        Counter counter = new MagicCounter(4);

        testCounter(counter, 10000, 4);
    }

    private void testCounter(Counter counter, int iters, int threadsCount) {
        Runnable increment = () -> {
            System.out.println(TwoThreadIds.me());
            for (int i = 0; i < iters; i++) {
                counter.increment();
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            threads.add(new Thread(increment));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long count = counter.getValue();
        System.out.println(count);
        assertEquals((long) iters * threadsCount, count);
    }
}
