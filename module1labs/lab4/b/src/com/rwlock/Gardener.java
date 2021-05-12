package com.rwlock;

public class Gardener extends Thread {

    final Garden garden;

    public Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < garden.getRows(); ++i)
                for (int j = 0; j < garden.getColumns(); ++j)
                    try {
                        if (garden.get(i,j) == 0)
                            garden.set(i, j, 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
