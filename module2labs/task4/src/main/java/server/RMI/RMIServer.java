package server.RMI;

import entities.Professor;
import entities.Subject;
import server.dao.ProfessorDAO;
import server.dao.SubjectDAO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer  extends UnicastRemoteObject implements RMIServerInterface {

    protected RMIServer() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<Subject> getAllSubjects() throws RemoteException {
        return SubjectDAO.getAllSubjects();
    }

    @Override
    public Subject findSubjectById(Long id) throws RemoteException {
        return SubjectDAO.findSubjectByID(id);
    }

    @Override
    public ArrayList<Professor> getAllProfessors() throws RemoteException {
        return ProfessorDAO.getAllProfessors();
    }

    @Override
    public Professor findProfessorById(Long id) throws RemoteException {
        return ProfessorDAO.findProfessorByID(id);
    }

    @Override
    public void addSubject(Subject subject) throws RemoteException {
        SubjectDAO.addSubject(subject.getName(), subject.getHours(), subject.getProfessor().getId());
    }

    @Override
    public void updateSubject(Subject subject) throws RemoteException {
        SubjectDAO.updateSubject(subject);
    }

    @Override
    public void addProfessor(Professor professor) throws RemoteException {
        ProfessorDAO.addProfessor(professor);
    }

    @Override
    public void updateProfessor(Professor professor) throws RemoteException {
        ProfessorDAO.updateProfessor(professor);
    }

    @Override
    public void deleteProfessorById(Long id) throws RemoteException {
        ProfessorDAO.deleteProfessorById(id);
    }

    @Override
    public void deleteSubjectById(Long id) throws RemoteException {
        SubjectDAO.deleteSubjectById(id);
    }

    public static void main(String[] args) throws RemoteException {
        RMIServer server = new RMIServer();
        Registry r = LocateRegistry.createRegistry(1235);
        r.rebind("department", server);
    }
}
