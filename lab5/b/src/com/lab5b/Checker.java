package com.lab5b;

import java.util.concurrent.atomic.AtomicBoolean;

public class Checker implements Runnable {
    StringThread[] threads = new StringThread[4];
    private final AtomicBoolean finished;

    public Checker(AtomicBoolean finished) {
        this.finished = finished;
    }

    public void setThreadAtIndex(int ind, StringThread thread) {
        threads[ind] = thread;
    }

    @Override
    public void run() {
        System.out.println("Checking..");

        int charsCount[] = new int[4];
        int numOfEqual = 1;
        for (int i = 0; i < 4; ++i) {
            String s = threads[i].getString();
            for (int j = 0; j < s.length(); ++j)
                if (s.charAt(j) == 'a' || s.charAt(j) == 'b')
                    charsCount[i]++;
            int k = 0;
            for (int j = 0; j < i; ++j)
                if (charsCount[i] == charsCount[j]) {
                    k++;
                }
            numOfEqual = Math.max(k + 1, numOfEqual);
            System.out.printf("Thread %d: %s. Number of 'a' & 'c': %d\n", i , s, charsCount[i]);
        }
        System.out.println("Equal counts in " + numOfEqual + " strings.");
        if (numOfEqual >= 3) {
            finished.set(true);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
