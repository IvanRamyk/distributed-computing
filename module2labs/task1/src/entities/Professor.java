package entities;

import java.util.Objects;
import java.util.List;

public class Professor {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Department department;
    private List<Subject> subjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", department=" + department +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor professor = (Professor) o;
        return age == professor.age && id.equals(professor.id) && firstName.equals(professor.firstName) && lastName.equals(professor.lastName) && department.equals(professor.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, department);
    }

    public Professor(Long id, String firstName, String lastName, int age, Department department, List<Subject> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.department = department;
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
