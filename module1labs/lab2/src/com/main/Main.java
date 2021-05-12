package com.main;

import java.util.ArrayList;
import java.util.Random;

class Unit {
    private String title;
    private int price;

    Unit (String s, int p) {
        title = s;
        price = p;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "title='" + title + '\'' +
                '}';
    }

    public int getPrice() {
        return price;
    }
}

public class Main {
    int calculated = 0;
    int price = 0;
    volatile boolean stockIsEmpty = false;
    volatile boolean allStored = false;
    volatile ArrayList<Unit> unitsAtStock = new ArrayList<>();
    volatile ArrayList<Unit> unitsWaiting = new ArrayList<>();
    volatile ArrayList<Unit> unitsStored = new ArrayList<>();

    class Ivanov implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (!unitsAtStock.isEmpty())
                        unit = unitsAtStock.remove(0);
                    else break;
                }
                System.out.println("Unit is token from the stock.");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    unitsWaiting.add(0, unit);
                }
                System.out.println("Unit is waiting fot storing..");
            }
            stockIsEmpty = true;
        }
    }

    class Petrov implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (!unitsWaiting.isEmpty())
                        unit = unitsWaiting.remove(0);
                    else if (stockIsEmpty) break;
                }
                if (unit != null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (this) {
                        unitsStored.add(unit);
                    }
                    System.out.println("Unit is stored");
                }
            }
            allStored = true;
        }
    }

    class Necheporuk implements Runnable {

        @Override
        public void run() {
            while (true)
            {
                Unit unit = null;
                synchronized (this) {
                    if (unitsStored.size() > calculated)
                        unit = unitsStored.get(calculated);
                    else if (allStored) break;
                }
                if (unit != null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    price += unit.getPrice();
                    calculated++;
                    System.out.println(price);
                }
            }
        }
    }

    public static void main(String[] args) {
        Main manager = new Main();
        Random random = new Random();
        for (int i = 0; i < 20; ++ i) {
            manager.unitsAtStock.add(new Unit("a", random.nextInt(100)));
        }
        new Thread(manager.new Ivanov()).start();
        new Thread(manager.new Petrov()).start();
        new Thread(manager.new Necheporuk()).start();
    }
}