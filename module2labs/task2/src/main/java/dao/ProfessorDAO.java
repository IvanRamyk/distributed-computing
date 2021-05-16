package dao;

import entities.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProfessorDAO {
    public static ArrayList<Professor> getAllProfessors(){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM professor";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ArrayList<Professor> professors = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int age = rs.getInt(4);
                //List<Actor> actors = ActorDAO.findActorsByFilmId(id);
                professors.add(new Professor(id, firstName, lastName, age, null));
            }
            st.close();
            cp.releaseConnection(connection);
            return professors;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public static Professor findProfessorByID(Long id){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT * FROM professor\n" +
                            "WHERE id = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Professor professor = null;
            while(rs.next()) {
                Long pId = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int age = rs.getInt(4);
                professor = new Professor(pId, firstName, lastName, age, null);
            }
            st.close();
            cp.releaseConnection(connection);
            return professor;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void addProfessor(Professor professor)  {
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "INSERT INTO professor(\n" +
                            "\t first_name, last_name, age)\n" +
                            "\tVALUES (?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, professor.getFirstName());
            st.setString(2, professor.getLastName());
            st.setInt(3, professor.getAge());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static void updateProfessor(Professor professor) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        Professor oldProfessor = findProfessorByID(professor.getId());
        if (oldProfessor == null) {
            return;
        }

        if (professor.getAge() != -1) {
            oldProfessor.setAge(professor.getAge());
        }

        if (professor.getFirstName() != null && !professor.getFirstName().equals("")) {
            oldProfessor.setFirstName(professor.getFirstName());
        }

        if (professor.getLastName() != null && !professor.getLastName().equals("")) {
            oldProfessor.setLastName(professor.getLastName());
        }

        try(Connection connection = cp.getConnection();) {
            String sql =
                    "UPDATE public.professor\n" +
                            "\tSET first_name=?, last_name=?, age=?\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, oldProfessor.getFirstName());
            st.setString(2, oldProfessor.getLastName());
            st.setInt(3, oldProfessor.getAge());
            st.setLong(4, oldProfessor.getId());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void deleteProfessorById(Long professorId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM professor\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, professorId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}