package com.lab5b;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        AtomicBoolean finished = new AtomicBoolean(false);
        Checker checker = new Checker(finished);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, checker);
        StringThread[] threads = new StringThread[4];
        Random random = new Random();
        for (int i = 0; i < 4; ++i) {
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < 6; ++j)
                s.append((char) ('a' + random.nextInt(4)));
            threads[i] = new StringThread(cyclicBarrier, s.toString(), finished);
            checker.setThreadAtIndex(i, threads[i]);
            threads[i].start();
        }
    }
}
