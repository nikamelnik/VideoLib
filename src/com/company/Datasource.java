package com.company;

import com.company.dbModel.ActorRepository;
import com.company.dbModel.DirectorRepository;
import com.company.dbModel.FilmRepository;
import com.company.dbModel.FilmsActorsRepository;

import java.sql.*;

public class Datasource {
    public static final String DB_NAME = "videoLib.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\ЦифроваяКомпания\\IdeaProjects\\VideoLib\\" + DB_NAME;

    private Connection conn;

    public Connection getConn() {
        return conn;
    }

    public Connection open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return conn;
        } catch (SQLException e) {

            System.err.println("Couldn't connect to database: " + e.getMessage());
            return null;
        }

    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void createTables() {
        try {
            //System.out.println(test);
            Statement statement = conn.createStatement();

            statement.execute("DROP TABLE IF EXISTS " + DirectorRepository.TABLE_DIRECTORS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + DirectorRepository.TABLE_DIRECTORS +
                    " (" + DirectorRepository.COLUMN_DIRECTOR_ID + " INTEGER PRIMARY KEY, " +
                    DirectorRepository.COLUMN_DIRECTOR_FULLNAME + " text, " +
                    DirectorRepository.COLUMN_DIRECTOR_DATE_OF_BIRTH + " text" +
                    " )");


            statement.execute("DROP TABLE IF EXISTS " + FilmRepository.TABLE_FILMS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + FilmRepository.TABLE_FILMS +
                    " (" + FilmRepository.COLUMN_FILM_ID + " INTEGER PRIMARY KEY, " +
                    FilmRepository. COLUMN_FILM_TITLE + " text, " +
                    FilmRepository.COLUMN_FILM_COUNTRY + " text, " +
                    FilmRepository.COLUMN_FILM_RELEASE_DATE + " INTEGER , " +
                    FilmRepository.COLUMN_FILM_DIRECTOR_ID + " INTEGER , " +
                    " FOREIGN KEY " + '(' + FilmRepository.COLUMN_FILM_DIRECTOR_ID + ')' +
                    " REFERENCES " + DirectorRepository.TABLE_DIRECTORS + '(' + DirectorRepository.COLUMN_DIRECTOR_ID + ')' +

                    " )");

            statement.execute("DROP TABLE IF EXISTS " + ActorRepository.TABLE_ACTORS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + ActorRepository.TABLE_ACTORS +
                    " (" +ActorRepository.COLUMN_ACTOR_ID + " INTEGER PRIMARY KEY, " +
                    ActorRepository.COLUMN_ACTOR_FULLNAME + " text, " +
                    ActorRepository.COLUMN_ACTOR_DATE_OF_BIRTH + " text" +
                    " )");


            statement.execute("DROP TABLE IF EXISTS " + FilmsActorsRepository.TABLE_IDS);
            statement.execute("CREATE TABLE IF NOT EXISTS " + FilmsActorsRepository.TABLE_IDS +
                    " (" +
                    FilmsActorsRepository.COLUMN_IDS_FILM + " INTEGER, " +
                    FilmsActorsRepository. COLUMN_IDS_ACTOR + " INTEGER, " +
                    " FOREIGN KEY " + '(' + FilmsActorsRepository.COLUMN_IDS_FILM + ')' +
                    " REFERENCES " + FilmRepository.TABLE_FILMS + '(' + FilmRepository.COLUMN_FILM_ID + ')' +
                    " FOREIGN KEY " + '(' + FilmsActorsRepository.COLUMN_IDS_ACTOR + ')' +
                    " REFERENCES " + ActorRepository.TABLE_ACTORS + '(' + ActorRepository.COLUMN_ACTOR_ID + ')' +
                    ")");


            statement.close();
        } catch (SQLException e) {
            System.out.println("TABLE CREATING ERROR: " + e.getMessage());
            e.printStackTrace();
        }

    }



}
