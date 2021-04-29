package models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Film {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", release=" + release +
                ", country='" + country + '\'' +
                ", actorList=" + actors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return getId() == film.getId() && Objects.equals(getName(), film.getName()) && Objects.equals(getRelease(), film.getRelease()) && Objects.equals(getCountry(), film.getCountry()) && Objects.equals(getActorList(), film.getActorList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRelease(), getCountry(), getActorList());
    }

    private LocalDateTime release;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRelease() {
        return release;
    }

    public void setRelease(LocalDateTime release) {
        this.release = release;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Actor> getActorList() {
        return actors;
    }

    public void setActorList(List<Actor> actorList) {
        this.actors = actorList;
    }

    private String country;

    public Film(Long id, String name, LocalDateTime release, String country, List<Actor> actorList) {
        this.id = id;
        this.name = name;
        this.release = release;
        this.country = country;
        this.actors = actorList;
    }

    private List<Actor> actors;



}
