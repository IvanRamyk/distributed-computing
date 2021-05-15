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
        subjects.add(new Subject(id, name, hours, professor));
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

    public int countSubjects() {
        return subjects.size();
    }

    public void deleteSubjectById(Long id) {
        subjects.removeIf(professor -> professor.getId().equals(id));
    }
}
