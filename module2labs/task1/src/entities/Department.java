package entities;

import java.util.ArrayList;

public class Department {
    private ArrayList<Professor> professors = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();


    public Department(ArrayList<Professor> professors, ArrayList<Subject> subjects) {
        this.professors = professors;
        this.subjects = subjects;
    }

    public Department() {
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void saveToFile(String filename) {

    }

    public void loadFromFile(String fileName) {

    }


    public void addProfessor(Long id, String firstName, String lastName, int age) {// explicit id
        for (Professor professor : professors) {
            if (professor.getId().equals(id)) {
                System.out.println("Professor with id: " +  id + " is already exists");
                return;
            }
        }

        professors.add(new Professor(id, firstName, lastName, age, new ArrayList<>()));
    }


    public Professor getProfessorById(Long id) {
        for (Professor professor : professors) {
            if (professor.getId().equals(id)) {
                return professor;
            }
        }
        return null;
    }

    public Professor getProfessorByName(String firstName, String lastName) {
        for (Professor professor : professors) {
            if (professor.getFirstName().equals(firstName) && professor.getLastName().equals(lastName)) {
                return professor;
            }
        }
        return null;
    }

    public int countProfessors() {
        return professors.size();
    }

    public void deleteProfessorsById(Long id) {
        deleteSubjectByProfessorId(id);
        professors.removeIf(professor -> professor.getId().equals(id));
    }

    public void addSubject(Long id, String name,  int hours, Long professorId) {
        Professor professor = null;
        for (Professor p : professors) {
            if (p.getId().equals(professorId)) {
                professor = p;
            }
        }
        if (professor == null) {
            System.out.println("Professor with id: " + professorId + " does not exist");
            return;
        }
        for (Subject subject : subjects) {
            if (subject.getId().equals(id)) {
                System.out.println("Subject with id: " +  id + " is already exists");
                return;
            }
        }
        Subject subject = new Subject(id, name, hours, professor);
        subjects.add(subject);
        professor.getSubjects().add(subject);
    }

    public Subject getSubjectById(Long id) {
        for (Subject subject : subjects) {
            if (subject.getId().equals(id)) {
                return subject;
            }
        }
        return null;
    }


    public Subject getSubjectByName(String string) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(string)) {
                return subject;
            }
        }
        return null;
    }

    public Long getNextProfessorId() {
        Long id = (long) countProfessors();
        while (getProfessorById(id) != null)
            id++;
        return id;
    }

    public Long getNextSubjectId() {
        Long id = (long) countSubjects();
        while (getSubjectById(id) != null)
            id++;
        return id;
    }


    public int countSubjects() {
        return subjects.size();
    }

    public void deleteSubjectById(Long id) {
        subjects.removeIf(subject -> subject.getId().equals(id));
    }

    public void deleteSubjectByProfessorId(Long id) {
        subjects.removeIf(subject -> subject.getProfessor().getId().equals(id));
    }

    public void updateSubject(Subject newSubject) {
        Subject oldSubject = getSubjectById(newSubject.getId());
        if (oldSubject == null) {
            return;
        }
        if (newSubject.getName() != null)
            oldSubject.setName(newSubject.getName());
        if (newSubject.getHours() != -1)
            oldSubject.setHours(newSubject.getHours());
        if (newSubject.getProfessor() != null) {
            Professor newProfessor = getProfessorById(newSubject.getProfessor().getId());
            oldSubject.setProfessor(newProfessor);
        }
    }

    public void updateProfessor(Professor newProfessor) {
        Professor professor = getProfessorById(newProfessor.getId());
        System.out.println(newProfessor);
        System.out.println(professor);
        if (professor == null) {
            return;
        }
        if (newProfessor.getFirstName() != null)
            professor.setFirstName(newProfessor.getFirstName());
        if (newProfessor.getLastName() != null)
            professor.setLastName(newProfessor.getLastName());
        if (newProfessor.getAge() != -1)
            professor.setAge(newProfessor.getAge());
    }
}
