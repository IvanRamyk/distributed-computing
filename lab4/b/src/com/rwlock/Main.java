package com.rwlock;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock lock = new ReadWriteLock();
        Garden garden = new Garden(10, 10, lock);
        Gardener gardener = new Gardener(garden);
        Nature nature = new Nature(garden, 5);
        Monitor1 monitor1 = new Monitor1(garden, "log");
        Monitor2 monitor2 = new Monitor2(garden);
        gardener.setDaemon(true);
        nature.setDaemon(true);
        monitor2.setDaemon(true);
        monitor1.setDaemon(true);
        gardener.start();
        nature.start();
        monitor1.start();
        monitor2.start();
        sleep(20 * 1000);
    }
}
