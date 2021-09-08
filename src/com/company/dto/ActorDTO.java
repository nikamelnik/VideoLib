package com.company.dto;


import com.company.dbModel.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActorDTO {
    private int id;
    private String fullName;
    private LocalDate dob;
    private List <FilmDTO> filmDTOs;

    public ActorDTO() {
        this.filmDTOs = new ArrayList<>();;

    }

    public ActorDTO(String fullName, LocalDate dob) {
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

        public List<FilmDTO> getFilmDTOs() {
        return filmDTOs;
    }

    public void addFilmDTO(FilmDTO filmDTO) {
        this.filmDTOs.add(filmDTO);
    }

    public String getFilmDTONames() {
        StringBuilder sb= new StringBuilder();
        for (FilmDTO filmDTO: filmDTOs){
            sb.append(filmDTO.getTitle());
            sb.append(", ");
        }

        return sb.toString();
    }

}