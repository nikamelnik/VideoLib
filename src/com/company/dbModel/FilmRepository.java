package com.company.dbModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmRepository {
    private Connection conn;
    DirectorRepository directorRepository;
    ActorRepository actorRepository;
    FilmsActorsRepository filmsActorsRepository;

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_FILM_ID = "_id";
    public static final String COLUMN_FILM_TITLE = "title";
    public static final String COLUMN_FILM_COUNTRY = "country";
    public static final String COLUMN_FILM_RELEASE_DATE = "release_date";
    public static final String COLUMN_FILM_DIRECTOR_ID = "director_id";

    public static final int INDEX_FILM_ID = 1;
    public static final int INDEX_FILM_TITLE = 2;
    public static final int INDEX_FILM_COUNTRY = 3;
    public static final int INDEX_FILM_RELEASE_DATE = 4;
    public static final int INDEX_FILM_DIRECTOR_ID = 5;


    private PreparedStatement insertIntoFilms;
    private PreparedStatement queryFilmByTitle;
    private PreparedStatement queryFilmById;
    private PreparedStatement queryFilmByRelease;
    private PreparedStatement updateFilm;
    private PreparedStatement deleteFilmsByID;
    private PreparedStatement deleteFilmsByNumOfYears;


    public static final String INSERT_FILM = "INSERT INTO " + TABLE_FILMS +
            '(' + COLUMN_FILM_TITLE + ", " + COLUMN_FILM_COUNTRY + ", " + COLUMN_FILM_RELEASE_DATE + ", " + COLUMN_FILM_DIRECTOR_ID + ") VALUES(?, ?, ?, ?)";

    public static final String QUERY_FILM_BY_TITLE = "SELECT " + COLUMN_FILM_ID + " FROM " + TABLE_FILMS +
            " WHERE " + COLUMN_FILM_TITLE + " = ?";

    public static final String QUERY_FILMS_BY_RELEASE = "SELECT " + COLUMN_FILM_ID + ", " + COLUMN_FILM_TITLE + ", " + COLUMN_FILM_COUNTRY + ", " + COLUMN_FILM_RELEASE_DATE + ", " +
            COLUMN_FILM_DIRECTOR_ID + " FROM " + TABLE_FILMS +
            " WHERE " + COLUMN_FILM_RELEASE_DATE + " >= ?" + " AND " + COLUMN_FILM_RELEASE_DATE + " <= ?";

    public static final String QUERY_FILMS_BY_ID = "SELECT " + COLUMN_FILM_ID + ", " + COLUMN_FILM_TITLE + ", " + COLUMN_FILM_COUNTRY + ", "
            + COLUMN_FILM_RELEASE_DATE + ", " + COLUMN_FILM_DIRECTOR_ID + " FROM " + TABLE_FILMS +
            " WHERE " + COLUMN_FILM_ID + " = ?";

    public static final String QUERY_FILMS_WHERE = "SELECT " + COLUMN_FILM_ID + ", " + COLUMN_FILM_TITLE + ", " + COLUMN_FILM_COUNTRY + ", " + COLUMN_FILM_RELEASE_DATE + ", " +
            COLUMN_FILM_DIRECTOR_ID + " FROM " + TABLE_FILMS +
            " WHERE ";

    public static final String UPDATE_FILM = "UPDATE " + TABLE_FILMS + " SET " + COLUMN_FILM_TITLE + " = ?" + ", " + COLUMN_FILM_COUNTRY + " = ?" + ", " +
            COLUMN_FILM_RELEASE_DATE + " = ?" + ", " + COLUMN_FILM_DIRECTOR_ID + " = ?" +
            " WHERE " + COLUMN_FILM_ID + " = ?";

    public static final String DELETE_FILMS_BY_ID = "DELETE FROM " + TABLE_FILMS + " WHERE " + COLUMN_FILM_ID + " = ?";
    public static final String DELETE_FILMS_BY_NUM_OF_YEARS = "DELETE FROM " + TABLE_FILMS + " WHERE " + COLUMN_FILM_RELEASE_DATE + " <= ?";


    public FilmRepository(Connection conn) {
        try {
            this.conn = conn;
            insertIntoFilms = conn.prepareStatement(INSERT_FILM);
            queryFilmByTitle = conn.prepareStatement(QUERY_FILM_BY_TITLE);
            queryFilmById = conn.prepareStatement(QUERY_FILMS_BY_ID);
            queryFilmByRelease = conn.prepareStatement(QUERY_FILMS_BY_RELEASE);
            updateFilm = conn.prepareStatement(UPDATE_FILM);
            deleteFilmsByNumOfYears = conn.prepareStatement(DELETE_FILMS_BY_NUM_OF_YEARS);
            deleteFilmsByID = conn.prepareStatement(DELETE_FILMS_BY_ID);

            actorRepository = new ActorRepository(conn);
            directorRepository = new DirectorRepository(conn);
            filmsActorsRepository = new FilmsActorsRepository(conn);

        } catch (SQLException e) {
            System.out.println("FilmRepository couldn't connect to database: " + e.getMessage());

        }
    }

    public void closeStatements() {
        try {
            if (insertIntoFilms != null) {
                insertIntoFilms.close();
            }
            if (queryFilmByTitle != null) {
                queryFilmByTitle.close();
            }
            if (queryFilmById != null) {
                queryFilmById.close();
            }
            if (queryFilmByRelease != null) {
                queryFilmByRelease.close();
            }
            if (updateFilm != null) {
                updateFilm.close();
            }

            if (deleteFilmsByNumOfYears != null) {
                deleteFilmsByNumOfYears.close();
            }
            if (deleteFilmsByID != null) {
                deleteFilmsByID.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close preparedStatements: " + e.getMessage());
        }
    }

    public Film createFilm(Film film) throws SQLException {

        try {
            insertIntoFilms.setString(1, film.getTitle());
            insertIntoFilms.setString(2, film.getCountry());
            insertIntoFilms.setInt(3, film.getReleaseYear());
            insertIntoFilms.setInt(4, film.getDirector().getId());

            int affectedRows = insertIntoFilms.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("The film insert failed");
            }
            ResultSet generatedKeys = insertIntoFilms.getGeneratedKeys();
            if (generatedKeys.next()) {
                film.setId(generatedKeys.getInt(1));
                return film;
            } else {
                throw new SQLException("Couldn't get _id for film");
            }

        } catch (Exception e) {
            System.out.println("Create film exception: " + e.getMessage());

        }
        return null;
    }

    public Film getFilm(int id) {
        try {
            queryFilmById.setInt(1, id);
            ResultSet results = queryFilmById.executeQuery();

            if (results.next()) {
                Film film = new Film();
                film.setId(results.getInt(INDEX_FILM_ID));
                film.setTitle(results.getString(INDEX_FILM_TITLE));
                film.setCountry(results.getString(INDEX_FILM_COUNTRY));
                film.setReleaseYear(results.getInt(INDEX_FILM_RELEASE_DATE));
                film.setDirector(directorRepository.getDirector(results.getInt(INDEX_FILM_DIRECTOR_ID)));
                film.setActors(actorRepository.queryActorsByFilmID(id));
                return film;
            }

        } catch (SQLException e) {
            System.out.println("Films's id: " + id + "doesn't exist" + e.getMessage());
        }
        return null;
    }


    public void updateFilm(Film film) {
        try {
            conn.setAutoCommit(false);
            updateFilm.setString(1, film.getTitle());
            updateFilm.setString(2, film.getCountry());
            updateFilm.setInt(3, film.getReleaseYear());
            updateFilm.setInt(4, film.getDirector().getId());
            updateFilm.setInt(5, film.getId());
            int affectedRows = updateFilm.executeUpdate();

//            int i = 0;
//            if (i == 0){
//                throw new RuntimeException("FAILED");
//            }

            List<Actor> actors = film.getActors();
            List<Integer> actorIds = new ArrayList<>();
            for (Actor actor : actors) {
                actorIds.add(actor.getId());
            }

            filmsActorsRepository.updateActorID(film.getId(), actorIds);


            if (affectedRows == 1) {
                conn.commit();
            }
        } catch (SQLException e) {
            System.out.println("Update film failed" + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException e2) {
                System.out.println("rollback failed" + e.getMessage());
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e2) {
                    System.out.println("Couldn't reset auto-commit!" + e2.getMessage());
                }
            }
        }
    }

    public void deleteFilms(int id) {
        try {
            deleteFilmsByID.setInt(1, id);
            deleteFilmsByID.execute();
        } catch (SQLException e) {
            System.out.println("Query DELETE failed: " + e.getMessage());

        }

    }

    public void deleteFilmsByNumOfYears(int numOfYears) {

        LocalDate yearsAgo = LocalDate.now().minusYears(numOfYears);
        int deleteBefore = yearsAgo.getYear();
        try {
            deleteFilmsByNumOfYears.setInt(1, deleteBefore);
            deleteFilmsByNumOfYears.execute();
        } catch (SQLException e) {
            System.out.println("Query DELETE failed: " + e.getMessage());

        }

    }

    public List<Film> queryFilms() {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_FILMS);
        sb.append(" ORDER BY ");
        sb.append(COLUMN_FILM_TITLE);
        sb.append(" COLLATE NOCASE ASC");

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            return getFilms(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Film> queryFilmsByReleaseDate(int yearFrom, int yearTo) {
        try {
            queryFilmByRelease.setInt(1, yearFrom);
            queryFilmByRelease.setInt(2, yearTo);
            ResultSet results = queryFilmByRelease.executeQuery();
            return getFilms(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return Collections.emptyList();
        }


    }


    public List<Film> queryFilmsByTitle(String title) {

        StringBuilder sb = new StringBuilder(QUERY_FILMS_WHERE);
        sb.append(COLUMN_FILM_TITLE);
        sb.append(" = \"");
        sb.append(title);
        sb.append('"');

        try {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString());
            return getFilms(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return Collections.emptyList();
        }

    }


    List<Film> getFilms(ResultSet results) throws SQLException {
        List<Film> films = new ArrayList<>();
        while (results.next()) {
            Film film = new Film();
            int id = results.getInt(INDEX_FILM_ID);
            film.setId(id);
            film.setTitle(results.getString(INDEX_FILM_TITLE));
            film.setReleaseYear(results.getInt(INDEX_FILM_RELEASE_DATE));
            film.setCountry(results.getString(INDEX_FILM_COUNTRY));

            int directorID = results.getInt(INDEX_FILM_DIRECTOR_ID);
            film.setDirector(directorRepository.getDirector(directorID));
            film.setActors(actorRepository.queryActorsByFilmID(id));
            films.add(film);
        }
        return films;
    }

    public List<Film> queryFilmsByActorId(int actorId) {
        try{
        List<Integer> filmIds = filmsActorsRepository.queryFilmIdByActorId(actorId);
        List<Film> films = new ArrayList<>();
        for (int filmId : filmIds) {
            films.add(getFilm(filmId));
        }
        return films;
    } catch (SQLException e) {
        System.out.println("Query failed: " + e.getMessage());
        return Collections.emptyList();
    }
    }
}