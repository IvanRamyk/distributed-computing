package main;

import DAO.ActorDAO;
import DAO.FilmDAO;
import models.Actor;
import models.Film;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*List<Actor> actors = ActorDAO.getAllActors();
        if (actors == null) {
            System.out.println("Govno!");
        }
        for (Actor actor : actors) {
            System.out.println(actor.toString());
        }*/

        List<Film> films = FilmDAO.getAllFilms();
        if (films == null) {
            System.out.println("Shit!");
        }
        for (Film film : films) {
            System.out.println(film.toString());
        }
    }
}
