package server.RMI;

import entities.Professor;
import entities.Subject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServerInterface extends Remote {
    public ArrayList<Subject> getAllSubjects() throws RemoteException;

    public Subject findSubjectById(Long id) throws RemoteException;

    public ArrayList<Professor> getAllProfessors() throws RemoteException;

    public Professor findProfessorById(Long id) throws RemoteException;

    public void addSubject(Subject subject) throws RemoteException;

    public void updateSubject(Subject subject) throws RemoteException;

    public void addProfessor(Professor professor) throws RemoteException;

    public void updateProfessor(Professor professor) throws RemoteException;

    public void deleteProfessorById(Long id)throws RemoteException;

    public void deleteSubjectById(Long id) throws RemoteException;

}
