package DAO;

import models.Actor;
import models.Film;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FilmDAO {
    public static List<Film> getAllFilms(){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM film";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Film> films = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                Timestamp release = rs.getTimestamp(3);
                String country = rs.getString(4);
                List<Actor> actors = ActorDAO.findActorsByFilmId(id);
                films.add(new Film(id, name, release.toLocalDateTime(), country, actors));
            }
            st.close();
            cp.releaseConnection(connection);
            return films;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void addFilm(Film film)  {
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "INSERT INTO film (name, release, country) "
                            + "VALUES (?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, film.getName());
            st.setTimestamp(2, Timestamp.valueOf(film.getRelease()));
            st.setString(3, film.getCountry());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static void updateFilm(Film film) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "UPDATE film\n" +
                            "\tSET name=?, release=?, country=?\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, film.getName());
            st.setTimestamp(2, Timestamp.valueOf(film.getRelease()));
            st.setString(3, film.getCountry());
            st.setLong(4, film.getId());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void deleteFilmById(Long filmId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM film\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, filmId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void addRole(Long filmId, Long actorId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "INSERT INTO public.role(\n" +
                            "\tactor_id, film_id)\n" +
                            "\tVALUES (?, ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, actorId);
            st.setLong(2, filmId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void deleteRole(Long filmId, Long actorId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM public.role\n" +
                            "\tWHERE film_id = ? and actor_id = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, filmId);
            st.setLong(2, actorId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static List<Film> getNLastYearsFilms(int N){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT id, name, release, country\n" +
                            "\tFROM public.film\n" +
                            "\tWHERE (? - EXTRACT(YEAR FROM release) < ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, LocalDateTime.now().getYear());
            st.setInt(2, N);
            ResultSet rs = st.executeQuery();
            List<Film> films = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                Timestamp release = rs.getTimestamp(3);
                String country = rs.getString(4);
                List<Actor> actors = ActorDAO.findActorsByFilmId(id);
                films.add(new Film(id, name, release.toLocalDateTime(), country, actors));
            }
            st.close();
            cp.releaseConnection(connection);
            return films;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void deleteNYearsFilms(int years) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM public.film\n" +
                            "\tWHERE (DATE_PART('year', '2021-01-01'::date) - DATE_PART('year', film.release::date)) > ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, years);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}