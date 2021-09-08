package com.company.dbModel;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Director {
    private int id;
    private String fullName;
    private LocalDate dob;
    private List<Film> films;

    public Director(String fullName, LocalDate dob) {
        this.fullName = fullName;
        this.dob = dob;
        this.films = new ArrayList<>();
    }

    public Director() {
        this.films = new ArrayList<>();
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Film> getFilms() {
        return films;
    }
    public void addFilm(Film film) {
        this.films.add(film);
    }


}
