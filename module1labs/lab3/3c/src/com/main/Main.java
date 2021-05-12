package com.main;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    static final int TOBACCO = 1;
    static final int MATCHES = 2;
    static final int PAPER = 3;

    private String componentToString(int component) {
        if (component == TOBACCO)
            return "TOBACCO";
        if (component == MATCHES)
            return "MATCHES";
        return "PAPER";
    }

    Semaphore[] semaphore = new Semaphore[4];

    Main() throws InterruptedException {
        for (int i=0; i<4; ++i)
            semaphore[i] = new Semaphore(1);
        semaphore[1].acquire();
        semaphore[2].acquire();
        semaphore[3].acquire();
        new Smoker(1).start();
        new Smoker(2).start();
        new Smoker(3).start();
        new Mediator().start();
    }

    public class Smoker extends Thread {
        int smokerComponent;

        Smoker(int o) {
            smokerComponent = o;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    semaphore[smokerComponent].acquire();
                    System.out.println(componentToString(smokerComponent) + " is smoking...");
                    sleep(1000);
                    System.out.println(componentToString(smokerComponent) + " finished smoking.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore[0].release();
            }
        }
    }

    public class Mediator extends Thread {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    semaphore[0].acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int componentA = random.nextInt(3) + 1;
                int componentB = random.nextInt(3) + 1;
                while (componentB == componentA) {
                    componentB = random.nextInt(3) + 1;
                }
                System.out.println("Components on the table: " + componentToString(componentA) + ", " + componentToString(componentB));
                int smoker = 6 - componentA - componentB;
                System.out.println(componentToString(smoker) + " turn");
                semaphore[smoker].release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main mediator = new Main();
    }
}