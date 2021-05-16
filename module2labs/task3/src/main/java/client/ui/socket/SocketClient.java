package client.ui.socket;


import entities.Professor;
import entities.Subject;
import server.dao.ProfessorDAO;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

// Client class
public class SocketClient {
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    Socket socket;

    public SocketClient() throws IOException {
        socket = new Socket("localhost", 1234);
        System.out.println("Connected to the server");
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public ArrayList<Professor> getAllProfessors() throws IOException, ClassNotFoundException {
        out.writeObject("professors");
        out.flush();
        return (ArrayList<Professor>) in.readObject();
    }

    public ArrayList<Subject> getAllSubjects() throws IOException, ClassNotFoundException {
        out.writeObject("subjects");
        out.flush();
        return  (ArrayList<Subject>) in.readObject();
    }

    public void addProfessor(Professor professor) throws IOException, ClassNotFoundException {
        out.writeObject("add-professor");
        out.writeObject(professor);
        out.flush();
        String status = (String) in.readObject();
    }

    public void deleteProfessorById(Long professorId) throws IOException, ClassNotFoundException {
        out.writeObject("delete-professor");
        out.writeObject(professorId);
        out.flush();
        String status = (String) in.readObject();
    }

    public Professor findProfessorById(Long professorId) throws IOException, ClassNotFoundException {
        out.writeObject("get-professor");
        out.writeObject(professorId);
        out.flush();
        return (Professor) in.readObject();
    }

    public void addSubject(Subject subject) throws IOException, ClassNotFoundException {
        out.writeObject("add-subject");
        out.writeObject(subject);
        out.flush();
        String status = (String) in.readObject();
    }

    public void deleteSubjectById(Long subject) throws IOException, ClassNotFoundException {
        out.writeObject("delete-subject");
        out.writeObject(subject);
        out.flush();
        String status = (String) in.readObject();
    }

    public void updateSubject(Subject subject) throws IOException, ClassNotFoundException {
        out.writeObject("update-subject");
        out.writeObject(subject);
        out.flush();
        String status = (String) in.readObject();
    }

    public void updateProfessor(Professor professor) throws IOException, ClassNotFoundException {
        out.writeObject("update-professor");
        out.writeObject(professor);
        out.flush();
        String status = (String) in.readObject();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        out.writeObject("close");
    }

    // driver code
}