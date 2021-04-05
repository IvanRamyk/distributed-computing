import java.util.ArrayList;
import java.util.Random;

class Tournament extends Thread {
    volatile private ArrayList<Integer> monks;
    volatile private int start, end;
    volatile private int winner = -1;

    Tournament(ArrayList<Integer> monks, int start, int end) {
        this.monks = monks;
        this.start = start;
        this.end = end;
    }

    Tournament(ArrayList<Integer> monks) {
        this.monks = monks;
        this.start = 0;
        this.end = monks.size();
    }

    @Override
    public void run() {
        if (end - start == 1)
            winner = start;
        else
        if (end - start == 2)
        {
            if (monks.get(start)>monks.get(start+1))
                winner = start;
            else winner = start + 1;
        }
        else {
            Tournament left = new Tournament(monks, start, (start+end)/2);
            Tournament right = new Tournament(monks, (start+end)/2, end);
            left.start();
            right.start();
            try {
                left.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                right.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (monks.get(left.getWinner()) > monks.get(right.getWinner()))
                winner = left.getWinner();
            else winner = right.getWinner();
        }
        System.out.println(winner + " won round with power " + monks.get(winner));
    }

    public int getWinner() {
        return winner;
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> monks = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            monks.add(random.nextInt(10000));
        }
        System.out.println("Monks powers: ");
        for (var m : monks) {
            System.out.printf("%d, ", m);
        }
        System.out.println();
        Tournament tournament = new Tournament(monks);
        tournament.start();
    }
}