package com.rwlock;

public class Monitor2 extends Thread {
    final Garden garden;

    public Monitor2(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < garden.getRows(); ++i) {
                for (int j = 0; j < garden.getColumns(); ++j)
                {
                    try {
                        System.out.print(garden.get(i, j) + " ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
            System.out.println();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
