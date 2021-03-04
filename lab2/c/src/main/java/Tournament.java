import java.util.ArrayList;

class Round extends Thread {

}

public class Tournament {
    private static ArrayList<Monk> monks = new ArrayList<>();

    private static void createMonks() {
        monks.add(new Monk("a", 1));
        monks.add(new Monk("a", 2));
        monks.add(new Monk("a", 3));
        monks.add(new Monk("a", 4));
        monks.add(new Monk("a", 5));
        monks.add(new Monk("a", 6));
        monks.add(new Monk("a", 7));
        monks.add(new Monk("a", 8));
    }


    public static void main(String[] args){
        createMonks();

    }
}
