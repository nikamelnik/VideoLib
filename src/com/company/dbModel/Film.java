package com.company.dbModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Film {

    private int id;
    private String title;
    private int releaseYear;
    private String country;

    private Director director;
    private List<Actor> actors;


    public Film() {
        this.actors = new ArrayList<>();
    }

    public Film(String title, int releaseYear, String country, Director director, List<Actor> actors) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.country = country;
        this.director = director;
        this.actors = actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {

        this.releaseYear = releaseYear;

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getStringActors() {
        StringBuilder sb= new StringBuilder();
        for (Actor actor:actors){
            sb.append(actor.getFullName());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void addActors(Actor actor) {
        this.actors.add(actor);
    }



}
