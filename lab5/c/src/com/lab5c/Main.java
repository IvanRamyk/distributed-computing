package com.lab5c;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        AtomicBoolean finished = new AtomicBoolean(false);
        Checker checker = new Checker(finished);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, checker);
        ArrayThread[] threads = new ArrayThread[4];
        Random random = new Random();
        for (int i = 0; i < 4; ++i) {
            ArrayList<Integer> array = new ArrayList<>();
            for (int j = 0; j < 2; ++j)
                array.add(random.nextInt(10));
            threads[i] = new ArrayThread(cyclicBarrier, array, finished);
            checker.setThreadAtIndex(i, threads[i]);
            threads[i].start();
        }
    }
}