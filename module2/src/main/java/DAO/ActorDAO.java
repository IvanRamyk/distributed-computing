package DAO;

import models.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ActorDAO {
    public static List<Actor> getAllActors(){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection()) {
            String sql =
                    "SELECT * "
                            + "FROM actor";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                Timestamp birthday = rs.getTimestamp(4);
                actors.add(new Actor(id, first_name, last_name, birthday.toLocalDateTime()));
            }
            st.close();
            cp.releaseConnection(connection);
            return actors;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<Actor> findActorsByFilmId(Long filmId){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection()) {
            String sql =
                    "SELECT * from actor \n" +
                            "INNER JOIN role\n" +
                            "ON actor.id = role.actor_id\n" +
                            "WHERE role.film_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, filmId);
            ResultSet rs = st.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                Timestamp birthday = rs.getTimestamp(4);
                actors.add(new Actor(id, first_name, last_name, birthday.toLocalDateTime()));
            }
            st.close();
            cp.releaseConnection(connection);
            return actors;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void addActor(Actor actor) {
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "INSERT INTO actor (first_name, second_name, birthdate) "
                            + "VALUES (?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, actor.getFirstName());
            st.setString(2, actor.getSecondName());
            st.setTimestamp(3, Timestamp.valueOf(actor.getBirthdate()));
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static void updateActor(Actor actor) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection()) {
            String sql =
                    "UPDATE actor\n" +
                            "\tSET first_name=?, second_name=?, birthdate=?\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, actor.getFirstName());
            st.setString(2, actor.getSecondName());
            st.setTimestamp(3, Timestamp.valueOf(actor.getBirthdate()));
            st.setLong(4, actor.getId());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void deleteActorById(int actorId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM actor\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, actorId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}