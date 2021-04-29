package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Actor {
    private Long id;
    private String firstName;
    private String secondName;
    private LocalDateTime birthdate;//TODO change type

    public Actor(Long id, String firstName, String secondName, LocalDateTime birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(getId(), actor.getId()) && Objects.equals(getFirstName(), actor.getFirstName()) && Objects.equals(getSecondName(), actor.getSecondName()) && Objects.equals(getBirthdate(), actor.getBirthdate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSecondName(), getBirthdate());
    }

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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }
}
