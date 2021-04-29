package socket;

import models.Actor;
import models.Film;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

// Client class
public class FilmsSocketClient {

    // driver code
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("Connected to the server");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Scanner consoleIn = new Scanner(System.in);

            boolean isClosed = false;
            while (!isClosed) {

                String action = consoleIn.nextLine();
                switch (action) {

                    case "exit" -> {
                        out.writeObject("/close");
                        isClosed = true;
                    }

                    case "films" -> {
                        out.writeObject("/films");
                        out.flush();
                        ArrayList<Film> films = (ArrayList<Film>) in.readObject();
                        for (Film film : films) {
                            System.out.println(film);
                        }
                    }

                    case "actors" -> {
                        out.writeObject("/actors");
                        out.flush();
                        ArrayList<Actor> actors = (ArrayList<Actor>) in.readObject();
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }


                    case "add-film" -> {
                        System.out.println("Enter film props");
                        System.out.println("Enter film name");
                        String filmName = consoleIn.nextLine();
                        Long id = -1L;
                        System.out.println("Enter film country");
                        String country = consoleIn.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now();
                        out.writeObject("/add-film");
                        out.writeObject(new Film(id, filmName, dateTime, country, new ArrayList<>()));
                        out.flush();
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }

                    case "delete-film" -> {
                        System.out.println("Enter film id");
                        Long id  = consoleIn.nextLong();
                        out.writeObject("/delete-film");
                        out.writeObject(id);
                        out.flush();;
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }

                    case "add-actor" -> {
                        System.out.println("Enter first name");
                        String first_name = consoleIn.nextLine();
                        System.out.println("Enter last name");
                        String second_name = consoleIn.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now();

                        out.writeObject("/add-actor");
                        out.writeObject(new Actor(-1L, first_name, second_name, dateTime));
                        out.flush();
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }

                    case "delete-actor" -> {
                        System.out.println("Enter actor id");
                        Long id  = consoleIn.nextLong();
                        out.writeObject("/delete-actor");
                        out.writeObject(id);
                        out.flush();;
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }

                    case "add-role" -> {
                        System.out.println("Enter actor's id");
                        Long actorId = consoleIn.nextLong();
                        System.out.println("Enter film id");
                        Long filmId = consoleIn.nextLong();

                        out.writeObject("/add-role");
                        out.writeObject(actorId);
                        out.writeObject(filmId);
                        out.flush();
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }

                    case "delete-role" -> {
                        System.out.println("Enter actor's id");
                        Long actorId = consoleIn.nextLong();
                        System.out.println("Enter film id");
                        Long filmId = consoleIn.nextLong();

                        out.writeObject("/delete-role");
                        out.writeObject(actorId);
                        out.writeObject(filmId);
                        out.flush();
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }


                    // Additional requests


                    case "recent-films" -> {
                        out.writeObject("/recent-films");
                        out.flush();
                        ArrayList<Film> films = (ArrayList<Film>) in.readObject();
                        for (Film film : films) {
                            System.out.println(film);
                        }
                    }

                    case "film-actors" -> {
                        System.out.println("Enter film id");
                        Long id = consoleIn.nextLong();
                        out.writeObject("/film-actors");
                        out.writeObject(id);
                        out.flush();
                        ArrayList<Actor> actors = (ArrayList<Actor>) in.readObject();
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }

                    case "n-films-actors" -> {
                        System.out.println("Enter number of films");
                        int n = consoleIn.nextInt();
                        out.writeObject("/n-films-actors");
                        out.writeObject(n);
                        out.flush();
                        ArrayList<Actor> actors = (ArrayList<Actor>) in.readObject();
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }

                    case "delete-n-years-films" -> {
                        System.out.println("Enter years");
                        int years = consoleIn.nextInt();
                        out.writeObject("/delete-n-years-films");
                        out.writeObject(years);
                        out.flush();
                        String status = (String) in.readObject();
                        System.out.println(status);
                    }


                }

            }
            out.close();
            in.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
