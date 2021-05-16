package server.dao;

import entities.Professor;
import entities.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectDAO {
    public static ArrayList<Subject> getAllSubjects(){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT subject.id, subject.name, subject.hours, professor.id, professor.first_name, professor.last_name, professor.age FROM subject\n" +
                            "INNER JOIN professor on professor.id = subject.professor_id;";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ArrayList<Subject> subjects = new ArrayList<>();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String name = rs.getString(2);
                int hours = rs.getInt(3);
                Long professorId = rs.getLong(4);
                String firstName = rs.getString(5);
                String lastName = rs.getString(6);
                int age = rs.getInt(7);
                subjects.add(new Subject(id, name, hours, new Professor(professorId, firstName, lastName, age, null)));
            }
            st.close();
            cp.releaseConnection(connection);
            return subjects;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }


    public static Subject findSubjectByID(Long id){
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "SELECT subject.id, subject.name, subject.hours, professor.id, professor.first_name, professor.last_name, professor.age FROM subject\n" +
                            "INNER JOIN professor on professor.id = subject.professor_id\n" +
                            "WHERE subject.id = ?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Subject subject = null;
            while(rs.next()) {
                id = rs.getLong(1);
                String name = rs.getString(2);
                int hours = rs.getInt(3);
                Long professorId = rs.getLong(4);
                String firstName = rs.getString(5);
                String lastName = rs.getString(6);
                int age = rs.getInt(7);
                subject = new Subject(id, name, hours, new Professor(professorId, firstName, lastName, age, null));
            }
            st.close();
            cp.releaseConnection(connection);
            return subject;
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void addSubject(String name, int hours, Long professorId)  {
        ConnectionPool cp = ConnectionPool.getConnectionPool();
        System.out.println(professorId);
        if (ProfessorDAO.findProfessorByID(professorId) == null) {
            return;
        }

        try(Connection connection = cp.getConnection();) {
            String sql =
                    "INSERT INTO subject(\n" +
                            "\tname, hours, professor_id)\n" +
                            "\tVALUES (?, ?, ?);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setInt(2, hours);
            st.setLong(3, professorId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static void updateSubject(Subject subject) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        Subject oldSubject = findSubjectByID(subject.getId());
        if (oldSubject == null) {
            return;
        }
        if (subject.getHours() != -1) {
            oldSubject.setHours(subject.getHours());
        }

        if (subject.getName() != null && !subject.getName().equals("")) {
            oldSubject.setName(subject.getName());
        }

        if (subject.getProfessor() != null) {
            oldSubject.setProfessor(subject.getProfessor());
        }

        try(Connection connection = cp.getConnection();) {
            String sql =
                    "UPDATE public.subject\n" +
                            "\tSET name=?, hours=?, professor_id=?\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, oldSubject.getName());
            st.setInt(2, oldSubject.getHours());
            st.setLong(3, oldSubject.getProfessor().getId());
            st.setLong(4, subject.getId());
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }

    public static void deleteSubjectById(Long subjectId) {

        ConnectionPool cp = ConnectionPool.getConnectionPool();
        try(Connection connection = cp.getConnection();) {
            String sql =
                    "DELETE FROM subject\n" +
                            "\tWHERE id=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, subjectId);
            int count = st.executeUpdate();
            st.close();
            cp.releaseConnection(connection);
        } catch (SQLException | InterruptedException e1) {
            e1.printStackTrace();
        }

    }
}
