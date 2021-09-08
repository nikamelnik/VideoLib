package com.company.service;

import com.company.dbModel.ActorRepository;
import com.company.dbModel.DirectorRepository;
import com.company.dbModel.Film;
import com.company.dbModel.FilmRepository;
import com.company.dto.DtoConverter;
import com.company.dto.FilmByYearDTO;
import com.company.dto.FilmDTO;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SearchFilm {

    FilmRepository filmRepository;
    ActorRepository actorRepository;
    DirectorRepository directorRepository;
    DtoConverter dtoConverter;

    public SearchFilm(Connection conn) {
        this.filmRepository = new FilmRepository(conn);
        this.actorRepository = new ActorRepository(conn);
        this.directorRepository = new DirectorRepository(conn);
        this.dtoConverter = new DtoConverter();
    }

    public List<FilmByYearDTO> groupFilmByRelease(int yearFrom, int yearTo) {
        List<Film> filmList = filmRepository.queryFilmsByReleaseDate(yearFrom, yearTo);
        List <FilmByYearDTO> films = new ArrayList<>();
      ///in process
        return films;
    }

    public List<FilmDTO> groupFilmByActor(int actorId) {
        List<Film> films = filmRepository.queryFilmsByActorId(actorId);
        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : films) {
            filmDTOS.add(dtoConverter.filmToDTO(film));
        }

        return filmDTOS;
    }

}
