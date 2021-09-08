package com.company.dto;


import java.util.ArrayList;
import java.util.List;

public class FilmDTO {

    private int id;
    private String title;
    private int releaseYear;
    private String country;

    private DirectorDTO directorDTO;
    private List<ActorDTO> actorDTOs;


    public FilmDTO() {
        this.actorDTOs = new ArrayList<>();
    }

    public FilmDTO(String title, int releaseYear, String country, DirectorDTO directorDTO, List<ActorDTO> actorDTOs) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.country = country;
        this.directorDTO = directorDTO;
        this.actorDTOs = actorDTOs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DirectorDTO getDirectorDTO() {
        return directorDTO;
    }

    public void setDirectorDTO(DirectorDTO directorDTO) {
        this.directorDTO = directorDTO;
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
        for (ActorDTO actorDTO:actorDTOs){
            sb.append(actorDTO.getFullName());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


    public List<ActorDTO> getActorDTOs() {
        return actorDTOs;
    }

    public void setActorDTOs(List<ActorDTO> actorDTOs) {
        this.actorDTOs = actorDTOs;
    }

    public void addActors(ActorDTO actorDTO) {
        this.actorDTOs.add(actorDTO);
    }



}
