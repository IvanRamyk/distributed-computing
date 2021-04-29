package RMI;

import models.Actor;
import models.Film;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class RMIClient {

    private RMIClient() {}

    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry(1235);
            RMIServerInterface stub = (RMIServerInterface) registry.lookup("films");

            Scanner consoleIn = new Scanner(System.in);

            boolean isClosed = false;
            while (!isClosed) {
                String action = consoleIn.nextLine();
                String status = "Ok";
                switch (action) {
                    case "exit" -> isClosed = true;

                    case "films" -> {
                        ArrayList<Film> films = stub.getFilms();
                        for (Film film : films) {
                            System.out.println(film);
                        }
                    }

                    case "actors" -> {
                        ArrayList<Actor> actors = stub.getActors();
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }

                    case "add-film" -> {
                        System.out.println("Enter film name");
                        String filmName = consoleIn.nextLine();
                        Long id = -1L;
                        System.out.println("Enter film country");
                        String country = consoleIn.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now();
                        stub.addFilm(new Film(id, filmName, dateTime, country, new ArrayList<>()));
                        System.out.println(status);
                    }

                    case "delete-film" -> {
                        System.out.println("Enter film id");
                        Long id  = consoleIn.nextLong();
                        stub.deleteFilm(id);
                        System.out.println(status);
                    }

                    case "add-actor" -> {
                        System.out.println("Enter first name");
                        String first_name = consoleIn.nextLine();
                        System.out.println("Enter last name");
                        String second_name = consoleIn.nextLine();
                        LocalDateTime dateTime = LocalDateTime.now();

                        stub.addActor(new Actor(-1L, first_name, second_name, dateTime));
                        System.out.println(status);
                    }

                    case "delete-actor" -> {
                        System.out.println("Enter actor id");
                        Long id  = consoleIn.nextLong();
                        stub.deleteActor(id);
                        System.out.println(status);
                    }

                    case "add-role" -> {
                        System.out.println("Enter actor's id");
                        Long actorId = consoleIn.nextLong();
                        System.out.println("Enter film id");
                        Long filmId = consoleIn.nextLong();
                        stub.addRole(filmId, actorId);
                        System.out.println(status);
                    }

                    case "delete-role" -> {
                        System.out.println("Enter actor's id");
                        Long actorId = consoleIn.nextLong();
                        System.out.println("Enter film id");
                        Long filmId = consoleIn.nextLong();
                        stub.deleteRole(filmId, actorId);
                        System.out.println(status);
                    }


                    // Additional requests


                    case "recent-films" -> {
                        ArrayList<Film> films = stub.getRecentFilms();
                        for (Film film : films) {
                            System.out.println(film);
                        }
                    }

                    case "film-actors" -> {
                        System.out.println("Enter film id");
                        Long id = consoleIn.nextLong();

                        ArrayList<Actor> actors = stub.getFilmActors(id);
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }

                    case "n-films-actors" -> {
                        System.out.println("Enter number of films");
                        int n = consoleIn.nextInt();
                        ArrayList<Actor> actors = stub.getNFilmsActors(n);
                        for (Actor actor : actors) {
                            System.out.println(actor);
                        }
                    }

                    case "delete-n-years-films" -> {
                        System.out.println("Enter years");
                        int years = consoleIn.nextInt();
                        stub.deleteNYearFilm(years);
                        System.out.println(status);
                    }


                }

            }



        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }
}