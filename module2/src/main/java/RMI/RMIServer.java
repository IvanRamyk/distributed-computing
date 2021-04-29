package RMI;

import DAO.ActorDAO;
import DAO.FilmDAO;
import models.Actor;
import models.Film;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class RMIServer extends UnicastRemoteObject implements RMIServerInterface{
    protected RMIServer() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<Actor> getActors() {
        return (ArrayList<Actor>) ActorDAO.getAllActors();
    }

    @Override
    public ArrayList<Actor> getFilmActors(Long filmId) {
        return (ArrayList<Actor>) ActorDAO.findActorsByFilmId(filmId);
    }

    @Override
    public ArrayList<Film> getFilms() {
        return (ArrayList<Film>) FilmDAO.getAllFilms();
    }

    @Override
    public ArrayList<Film> getRecentFilms() {
        return (ArrayList<Film>) FilmDAO.getNLastYearsFilms(2);
    }

    @Override
    public void addActor(Actor actor) {
        ActorDAO.addActor(actor);
    }

    @Override
    public void addFilm(Film film) {
        FilmDAO.addFilm(film);
    }

    @Override
    public void deleteFilm(Long filmId) {
        FilmDAO.deleteFilmById(filmId);
    }

    @Override
    public void deleteActor(Long actorId) {
        ActorDAO.deleteActorById(actorId);
    }

    @Override
    public void addRole(Long filmId, Long actorId) {
        FilmDAO.addRole(filmId, actorId);
    }

    @Override
    public void deleteRole(Long filmId, Long actorId) {
        FilmDAO.deleteRole(filmId, actorId);
    }

    @Override
    public void deleteNYearFilm(int years) {
        FilmDAO.deleteNYearsFilms(years);
    }

    public static void main(String[] args) throws RemoteException {
        RMIServer server = new RMIServer();
        Registry r = LocateRegistry.createRegistry(1235);
        r.rebind("films", server);
    }
}
