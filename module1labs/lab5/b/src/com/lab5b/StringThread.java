package com.lab5b;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class StringThread extends Thread {
    private final CyclicBarrier barrier;
    private String string;
    private final AtomicBoolean finished;

    public StringThread(CyclicBarrier barrier, String string, AtomicBoolean finished) {
        this.barrier = barrier;
        this.string = string;
        this.finished = finished;
    }

    public String getString() {
        return  string;
    }

    @Override
    public void run() {
        while (!finished.get()) {
            Random random = new Random();
            int i = random.nextInt(string.length());
            int prob = random.nextInt(4);
            if (prob > 0) {
                char ch = string.charAt(i);
                char res_ch = 'a';
                if (ch == 'a')
                    res_ch = 'c';
                if (ch == 'b')
                    res_ch = 'd';
                if (ch == 'd')
                    res_ch = 'b';

                string = string.substring(0, i) + res_ch + string.substring(i + 1);
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
