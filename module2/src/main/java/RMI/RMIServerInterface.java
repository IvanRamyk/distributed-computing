package RMI;

import models.Actor;
import models.Film;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServerInterface extends Remote {
    public ArrayList<Actor> getActors() throws RemoteException;

    public ArrayList<Actor> getFilmActors(Long filmId) throws RemoteException;

    public ArrayList<Film> getFilms() throws RemoteException;

    public ArrayList<Film> getRecentFilms() throws RemoteException;

    public void addActor(Actor actor) throws RemoteException;

    public void addFilm(Film film) throws RemoteException;

    public void deleteFilm(Long filmId)throws RemoteException;

    public void deleteActor(Long actorId) throws RemoteException;

    public void addRole(Long filmId, Long actorId) throws RemoteException;

    public void deleteRole(Long filmId, Long actorId) throws RemoteException;

    public void deleteNYearFilm(int years) throws RemoteException;
}
