package com.rwlock;

import java.util.Random;

public class Nature extends Thread {
    final Garden garden;
    final int numberWitheredFlowers;

    public Nature(Garden garden, int numberWitheredFlowers) {
        this.garden = garden;
        this.numberWitheredFlowers = numberWitheredFlowers;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {

            for (int i = 0; i < numberWitheredFlowers; ++i) {
                int x = random.nextInt(garden.rows);
                int y = random.nextInt(garden.columns);
                try {
                    garden.set(x, y, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}