package com.company.dto;


import com.company.dbModel.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DirectorDTO {
    private int id;
    private String fullName;
    private LocalDate dob;
    private List<FilmDTO> filmDTOS;

    public DirectorDTO(String fullName, LocalDate dob) {
        this.fullName = fullName;
        this.dob = dob;
        this.filmDTOS = new ArrayList<>();
    }

    public DirectorDTO() {
        this.filmDTOS = new ArrayList<>();
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

    public List<FilmDTO> getFilmDTOs() {
        return filmDTOS;
    }
    public void addFilm(FilmDTO filmDTO) {
        this.filmDTOS.add(filmDTO);
    }


}
