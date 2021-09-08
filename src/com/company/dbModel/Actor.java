package com.company.dbModel;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    private int id;
    private String fullName;
    private LocalDate dob;
    private List <Film> films;


    public Actor() {
        this.films = new ArrayList<>();;

    }

    public Actor(String fullName, LocalDate dob) {
        this.fullName = fullName;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob.toString();
    }

    public void setDob(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dob = LocalDate.parse(dob, formatter);
    }

        public List<Film> getFilms() {
        return films;
    }

    public void addFilm(Film film) {
        this.films.add(film);
    }


    public String getFilmsNames() {
        StringBuilder sb= new StringBuilder();
        for (Film film:films){
            sb.append(film.getTitle());
            sb.append(", ");
        }

        return sb.toString();
    }


}