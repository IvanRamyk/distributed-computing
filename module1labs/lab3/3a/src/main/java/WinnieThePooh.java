import java.util.concurrent.Semaphore;

class Pot {
    private final int capacity;
    private int currentVolume;


    public Pot(int capacity) {
        this.capacity = capacity;
    }

    public void putHoney(int volume) {
        currentVolume = Math.min(capacity, currentVolume + volume);
        System.out.printf("current volume %d\n", currentVolume);
    }

    public boolean isFull() {
        return currentVolume == capacity;
    }

    public void eatHoney() {
        currentVolume = 0;
    }
}




public class WinnieThePooh {
    private static final Pot pot = new Pot(10);
    private static final Semaphore semaphore = new Semaphore(1, true);
    private static boolean isFinished = false;

    public static class Bear extends Thread {
        
        private final int numberOfIterations;

        public Bear(int numberOfIterations) {
            this.numberOfIterations = numberOfIterations;
        }

        @Override
        public void run() {
            System.out.print("Start waiting for honey\n");
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < numberOfIterations; ++i) {
                try {
                    semaphore.release();
                    synchronized (pot) {
                        pot.eatHoney();
                        System.out.print("Eating honey\n");
                    }
                    Thread.sleep(3000);
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isFinished = true;
        }
    }

    public static class Bee extends Thread {

        @Override
        public void run() {
            while (!isFinished) {
                try {
                    Thread.sleep(1000);
                    synchronized (pot) {
                        pot.putHoney(1);
                        if (pot.isFull()) {
                            semaphore.release();
                            semaphore.acquire();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int N = 3;
        semaphore.acquire();
        Bear bear = new Bear(40);
        bear.start();
        for (int i = 0; i < N; ++i) {
            Bee bee = new Bee();
            bee.setDaemon(false);
            bee.start();
        }
    }
}
