package com.lab5c;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Checker implements Runnable {
    ArrayThread[] threads = new ArrayThread[4];
    private final AtomicBoolean finished;

    public Checker(AtomicBoolean finished) {
        this.finished = finished;
    }

    public void setThreadAtIndex(int ind, ArrayThread thread) {
        threads[ind] = thread;
    }

    @Override
    public void run() {
        System.out.println("Checking..");

        int[] sums = new int[3];
        boolean isEqual = true;
        for (int i = 0; i < 3; ++i) {
            System.out.printf("Thread %d: ", i);
            List<Integer> arr = threads[i].getArray();
            for (Integer integer : arr) {
                sums[i] += integer;
                System.out.printf("%d, ", integer);
            }
            System.out.printf("\nSum is %d\n", sums[i]);
            for (int j = 0; j < i; ++j) {
                if (sums[i] != sums[j]) {
                    isEqual = false;
                }
            }
        }
        if (isEqual) {
            finished.set(true);
        }
    }
}