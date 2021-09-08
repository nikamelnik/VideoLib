package com.company;

import com.company.dbModel.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.company.dbModel.ActorRepository.ACTOR_IS_DIRECTOR;
import static com.company.dbModel.FilmRepository.QUERY_FILMS_BY_RELEASE;

public class Main {

    public static void main(String[] args) {

        Datasource datasource = new Datasource();
        InsertData insertData = new InsertData();
        Connection conn = datasource.open();

        datasource.createTables();
        insertData.insertDataToDB(conn);
        ActorRepository actorRepository = new ActorRepository(conn);
        DirectorRepository directorRepository = new DirectorRepository(conn);
        FilmRepository filmRepository = new FilmRepository(conn);
        FilmsActorsRepository filmsActorsRepository = new FilmsActorsRepository(conn);

        System.out.println(" ____________________FILM BY RELEASE DATE_______________________");
        List<Film> film1999 = filmRepository.queryFilmsByReleaseDate(1999, 2003);
        if (film1999 == null) {
            System.out.println("No films released in this year");
            return;
        }
        for (Film film : film1999) {
            System.out.println("ID = " + film.getId() + ", Title = " + film.getTitle() + ", Release Date: " + film.getReleaseYear() +
                    ", Country: " + film.getCountry() + ", Director: " + film.getDirector().getFullName() + ", Actors : " + film.getStringActors());
        }

        System.out.println(" ____________________FILM BY TITLE__________________________");
        List<Film> film1 = filmRepository.queryFilmsByTitle("The Shawshank Redemption");
        if (film1 == null) {
            System.out.println("No film!");
            return;
        }
        for (Film film : film1) {
            System.out.println("Title = " + film.getTitle() + ", Actors : " + film.getActors());
        }

        System.out.println(" ____________________ALL ACTORS_______________________");
        List<Actor> actors = actorRepository.queryActors();
        if (actors == null) {
            System.out.println("No actors!");
            return;
        }
        for (Actor actor : actors) {
            System.out.println("ID = " + actor.getId() + ", Name = " + actor.getFullName() + ", Date of birth: " + actor.getDob());
        }

        System.out.println(" ____________________ACTORS who starred in 2 or more films_______________________");
        List<Actor> actors2 = actorRepository.queryActorsInNFilms(2);
        if (actors2 == null) {
            System.out.println("No actors!");
            return;
        }
        for (Actor actor : actors2) {
            System.out.println("ID = " + actor.getId() + ", Name = " + actor.getFullName() + ", Date of birth: " + actor.getDob());
        }

        System.out.println(" ____________________ACTORS who were directors_______________________");
        List<Actor> actors3 = actorRepository.queryActorsDirectors();
        if (actors3 == null) {
            System.out.println("No actors!");
            return;
        }
        for (Actor actor : actors3) {
            System.out.println("ID = " + actor.getId() + ", Name = " + actor.getFullName() + ", Date of birth: " + actor.getDob());
        }


        //   filmRepository.deleteFilmsByNumOfYears(20);


        // UPDATE FILM
        ArrayList<Actor> actors1 = new ArrayList<>();
        actors1.add(actorRepository.getActor(5));
        actors1.add(actorRepository.getActor(8));
       // actors1.add(actorRepository.getActor(3));

        Film filmToUpdate = filmRepository.getFilm(5);

        filmToUpdate.setTitle(" updated test");
        filmToUpdate.setCountry("updated country");
        filmToUpdate.setReleaseYear(2021);
        filmToUpdate.setDirector(directorRepository.getDirector(1));
        filmToUpdate.setActors(actors1);

        System.out.println("____________________________");
        System.out.println("UPDATE FILM ID = " + filmToUpdate.getId() + ", Title = " + filmToUpdate.getTitle() + ", Release Date: " + filmToUpdate.getReleaseYear() +
                ", Country: " + filmToUpdate.getCountry() + ", Director: " + filmToUpdate.getDirector().getFullName());

        filmRepository.updateFilm(filmToUpdate);


        System.out.println(" ____________________ALL FILMS_______________________");
        List<Film> films = filmRepository.queryFilms();
        if (films == null) {
            System.out.println("No films!");
            return;
        }
        for (Film film : films) {
            System.out.println("ID = " + film.getId() + ", Title = " + film.getTitle() + ", Release Date: " + film.getReleaseYear() +
                    ", Country: " + film.getCountry() + ", Director: " + film.getDirector().getFullName());
        }

        System.out.println(" ____________________ACTOR " + actorRepository.getActor(5).getFullName() +   "'s FILMS_______________________");

        List<Film> films1 = filmRepository.queryFilmsByActorId(5);
        if (films1 == null) {
            System.out.println("No films!");
            return;
        }
        for (Film film : films1) {
            System.out.println("ID = " + film.getId() + ", Title = " + film.getTitle() + ", Release Date: " + film.getReleaseYear() +
                    ", Country: " + film.getCountry() + ", Director: " + film.getDirector().getFullName());
        }


        System.out.println(QUERY_FILMS_BY_RELEASE);

        datasource.close();
    }

}