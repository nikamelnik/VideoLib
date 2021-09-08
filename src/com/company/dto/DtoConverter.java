package com.company.dto;

import com.company.dbModel.Actor;
import com.company.dbModel.Director;
import com.company.dbModel.Film;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {


    public FilmDTO filmToDTO(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(film.getId());
        filmDTO.setTitle(film.getTitle());
        filmDTO.setReleaseYear(film.getReleaseYear());
        filmDTO.setCountry(film.getCountry());
        filmDTO.setDirectorDTO(directorToDTO(film.getDirector()));
        filmDTO.setActorDTOs(actorToDTO(film.getActors()));
        return filmDTO;

    }


    public Film filmDtoToFilm(FilmDTO filmDTO) {
        Film film = new Film();

        film.setId(filmDTO.getId());
        film.setTitle(filmDTO.getTitle());
        film.setReleaseYear(filmDTO.getReleaseYear());
        film.setCountry(filmDTO.getCountry());
        film.setDirector(directorDtoToDirector(filmDTO.getDirectorDTO()));
        film.setActors(actorDtoToActor(filmDTO.getActorDTOs()));

        return film;
    }

    private ActorDTO actorToDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setFullName(actor.getFullName());
        actorDTO.setDob(actor.getDob());

        return actorDTO;
    }

    private Actor actorDtoToActor(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setId(actorDTO.getId());
        actor.setFullName(actorDTO.getFullName());
        actor.setDob(actorDTO.getDob());

        return actor;
    }

    public List<ActorDTO> actorToDTO(List<Actor> actors) {
        List<ActorDTO> actorDTOs = new ArrayList<>();
        for (Actor actor : actors) {
            actorDTOs.add(actorToDTO(actor));
        }
        return actorDTOs;
    }

    public List<Actor> actorDtoToActor(List<ActorDTO> actorDTOs) {
        List<Actor> actors = new ArrayList<>();
        for (ActorDTO actorDTO : actorDTOs) {
            actors.add(actorDtoToActor(actorDTO));
        }
        return actors;
    }


    public DirectorDTO directorToDTO(Director director) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(director.getId());
        directorDTO.setFullName(director.getFullName());
        directorDTO.setDob(director.getDob());

        return directorDTO;
    }

    public Director directorDtoToDirector(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setFullName(directorDTO.getFullName());
        director.setDob(directorDTO.getDob());

        return director;
    }

}
