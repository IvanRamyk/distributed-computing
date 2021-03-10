package com.lab5c;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ArrayThread extends Thread {
    private final CyclicBarrier barrier;
    private List<Integer> array;
    private final AtomicBoolean finished;

    public ArrayThread(CyclicBarrier barrier, List<Integer> string, AtomicBoolean finished) {
        this.barrier = barrier;
        this.array = string;
        this.finished = finished;
    }

    public List<Integer> getArray() {
        return  array;
    }

    @Override
    public void run() {
        while (!finished.get()) {
            Random random = new Random();
            int i = random.nextInt(array.size());
            int prob = random.nextInt(4);
            if (prob > 0) {
                int diff = 1;
                if (random.nextBoolean())
                    diff = -1;
                array.set(i, array.get(i) + diff);
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}