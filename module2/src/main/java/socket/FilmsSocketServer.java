package socket;

import DAO.ActorDAO;
import DAO.FilmDAO;
import models.Actor;
import models.Film;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Server class
public class FilmsSocketServer {
    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            while (true) {

                Socket client = server.accept();

                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                ClientHandler clientSock
                        = new ClientHandler(client);

                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public boolean iteration() {
            try {
                String request = (String) in.readObject();
                System.out.println("Request from clint: " + request);

                switch (request) {
                    //CRUD operations
                    case "/films" -> {
                        List<Film> films = FilmDAO.getAllFilms();
                        out.writeObject(films);
                    }
                    case "/actors" -> {
                        List<Actor> actors = ActorDAO.getAllActors();
                        out.writeObject(actors);
                    }
                    case "/add-film" -> {
                        Film film = (Film) in.readObject();
                        FilmDAO.addFilm(film);
                        out.writeObject("Ok");
                    }
                    case "/delete-film" -> {
                        Long filmId = (Long) in.readObject();
                        FilmDAO.deleteFilmById(filmId);
                        out.writeObject("Ok");
                    }
                    case "/add-actor" -> {
                        Actor actor = (Actor) in.readObject();
                        ActorDAO.addActor(actor);
                        out.writeObject("Ok");
                    }
                    case "/delete-actor" -> {
                        Long actorId = (Long) in.readObject();
                        ActorDAO.deleteActorById(actorId);
                        out.writeObject("Ok");
                    }

                    case "/add-role" -> {
                        Long actorId = (Long) in.readObject();
                        Long filmId = (Long) in.readObject();
                        FilmDAO.addRole(filmId, actorId);
                        out.writeObject("Ok");
                    }

                    case "/delete-role" -> {
                        Long actorId = (Long) in.readObject();
                        Long filmId = (Long) in.readObject();
                        FilmDAO.deleteRole(filmId, actorId);
                        out.writeObject("Ok");
                    }

                    case "/close" -> {
                        out.writeObject("Ok");
                        return false;
                    }
                    // END of CRUD operations

                }
                out.flush();
            }  catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return true;
        }

        public void run()
        {

            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("In&out streams are init");
                while (iteration()) {};
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}