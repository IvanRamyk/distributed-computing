package server.socket;


import entities.Professor;
import entities.Subject;
import server.dao.ProfessorDAO;
import server.dao.SubjectDAO;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server class
public class SocketServer {
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
                    case "professors" -> {
                        ArrayList<Professor> professors = ProfessorDAO.getAllProfessors();
                        out.writeObject(professors);
                    }

                    case "get-professor" -> {
                        Long id = (long) in.readObject();
                        out.writeObject(ProfessorDAO.findProfessorByID(id));
                    }

                    case "subjects" -> {
                        ArrayList<Subject> subjects = SubjectDAO.getAllSubjects();
                        out.writeObject(subjects);
                    }

                    case "add-professor" -> {
                        Professor professor = (Professor) in.readObject();
                        ProfessorDAO.addProfessor(professor);
                        out.writeObject("Ok");
                    }

                    case "update-professor" -> {
                        Professor professor = (Professor) in.readObject();
                        ProfessorDAO.updateProfessor(professor);
                        out.writeObject("Ok");
                    }
                    case "delete-professor" -> {
                        Long id = (Long) in.readObject();
                        ProfessorDAO.deleteProfessorById(id);
                        out.writeObject("Ok");
                    }

                    case "add-subject" -> {
                        Subject subject = (Subject) in.readObject();
                        SubjectDAO.addSubject(subject.getName(), subject.getHours(), subject.getProfessor().getId());
                        out.writeObject("Ok");
                    }

                    case "update-subject" -> {
                        Subject subject = (Subject) in.readObject();
                        SubjectDAO.updateSubject(subject);
                        out.writeObject("Ok");
                    }
                    case "delete-subject" -> {
                        Long id = (Long) in.readObject();
                        SubjectDAO.deleteSubjectById(id);
                        out.writeObject("Ok");
                    }

                    case "close" -> {
                        out.writeObject("Ok");
                        return false;
                    }

                }
                out.flush();
            }  catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return false;
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