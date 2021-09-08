package com.company;

import com.company.dbModel.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InsertData {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void insertDataToDB(Connection conn) {

        ActorRepository actorRepository = new ActorRepository(conn);
        DirectorRepository directorRepository = new DirectorRepository(conn);
        FilmRepository filmRepository = new FilmRepository(conn);
        FilmsActorsRepository filmsActorsRepository = new FilmsActorsRepository(conn);
        try {
            actorRepository.createActor(new Actor("Tom Hanks", LocalDate.parse(("09/07/1956"), formatter)));
            actorRepository.createActor(new Actor("David Morse", LocalDate.parse(("11/10/1953"), formatter)));
            actorRepository.createActor(new Actor("Bonnie Hunt", LocalDate.parse(("22/09/1961"), formatter)));
            actorRepository.createActor(new Actor("Michael Clarke Duncan", LocalDate.parse(("10/12/1957"), formatter)));
            actorRepository.createActor(new Actor("James Cromwell", LocalDate.parse(("27/01/1940"), formatter)));
            actorRepository.createActor(new Actor("Michael Jeter", LocalDate.parse(("26/08/1952"), formatter)));
            actorRepository.createActor(new Actor("Tim Robbins", LocalDate.parse(("16/10/1958"), formatter)));
            actorRepository.createActor(new Actor("Morgan Freeman", LocalDate.parse(("01/06/1937"), formatter)));
            actorRepository.createActor(new Actor("Elijah Wood", LocalDate.parse(("28/01/1981"), formatter)));
            actorRepository.createActor(new Actor("Viggo Mortensen", LocalDate.parse(("20/10/1958"), formatter)));

        } catch (SQLException e) {
            System.out.println("Problems with create actor: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            directorRepository.createDirector(new Director("Tom Hanks", LocalDate.parse(("09/07/1956"), formatter)));
            directorRepository.createDirector(new Director("Michael Clarke Duncan", LocalDate.parse(("10/12/1957"), formatter)));
            directorRepository.createDirector(new Director("Frank Darabont", LocalDate.parse(("28/01/1959"), formatter)));
            directorRepository.createDirector(new Director("Peter Jackson", LocalDate.parse(("31/10/1961"), formatter)));
            directorRepository.createDirector(new Director("Elijah Wood", LocalDate.parse(("28/01/1981"), formatter)));

            } catch (SQLException e) {
            System.out.println("Problems with insert director: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            filmsActorsRepository.insertID(1, 1);
            filmsActorsRepository.insertID(1, 2);
            filmsActorsRepository.insertID(1, 3);
            filmsActorsRepository.insertID(1, 4);
            filmsActorsRepository.insertID(1, 5);
            filmsActorsRepository.insertID(1, 6);
            filmsActorsRepository.insertID(2, 7);
            filmsActorsRepository.insertID(2, 8);
            filmsActorsRepository.insertID(3, 9);
            filmsActorsRepository.insertID(3, 10);
            filmsActorsRepository.insertID(4, 9);
            filmsActorsRepository.insertID(4, 10);
            filmsActorsRepository.insertID(5, 9);
            filmsActorsRepository.insertID(5, 10);

        } catch (SQLException e) {
            System.out.println("Problems with insert IDs: " + e.getMessage());
            e.printStackTrace();
        }

            ArrayList <Actor> actors1 = new ArrayList<>();
            actors1.add(actorRepository.getActor(9));
            actors1.add(actorRepository.getActor(10));

            ArrayList <Actor> actors2 = new ArrayList<>();
            actors2.add(actorRepository.getActor(1));
            actors2.add(actorRepository.getActor(2));
            actors2.add(actorRepository.getActor(3));
            actors2.add(actorRepository.getActor(4));
            actors2.add(actorRepository.getActor(5));
            actors2.add(actorRepository.getActor(6));

            ArrayList <Actor> actors3 = new ArrayList<>();
            actors3.add(actorRepository.getActor(7));
            actors3.add(actorRepository.getActor(8));

            try {
            filmRepository.createFilm(new Film("The Green Mile", 1999, "USA", directorRepository.getDirector(3),actors2));
            filmRepository.createFilm(new Film("The Shawshank Redemption", 1994, "USA", directorRepository.getDirector(1),actors3));
            filmRepository.createFilm(new Film("The Lord of the Rings: The Return of the King",2002, "New Zealand", directorRepository.getDirector(4),actors1));
            filmRepository.createFilm(new Film("The Lord of the Rings: The Two Towers",2002, "New Zealand", directorRepository.getDirector(4),actors1));
            filmRepository.createFilm(new Film("test film", 2020, "Belarus", directorRepository.getDirector(5),actors1));

        } catch (SQLException e) {
            System.out.println("Problems with insert film: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
