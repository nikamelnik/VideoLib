package com.company.dbModel;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.dbModel.DirectorRepository.*;
import static com.company.dbModel.FilmRepository.*;
import static com.company.dbModel.FilmsActorsRepository.*;

public class ActorRepository {
    private Connection conn;
    public static final String TABLE_ACTORS = "actors";
    public static final String COLUMN_ACTOR_ID = "_id";
    public static final String COLUMN_ACTOR_FULLNAME = "full_name";
    public static final String COLUMN_ACTOR_DATE_OF_BIRTH = "date_of_birth";
    public static final int INDEX_ACTOR_ID = 1;
    public static final int INDEX_ACTOR_FULLNAME = 2;
    public static final int INDEX_ACTOR_DATE_OF_BIRTH = 3;

    private PreparedStatement insertIntoActors;
    private PreparedStatement queryActorByName;
    private PreparedStatement queryActorByID;
    private PreparedStatement queryActorByFilmID;
    private PreparedStatement updateActor;
    private PreparedStatement deleteActor;
    private PreparedStatement countFilms;

    public static final String INSERT_ACTOR = "INSERT INTO " + TABLE_ACTORS +
            '(' + COLUMN_ACTOR_FULLNAME + ", " + COLUMN_ACTOR_DATE_OF_BIRTH + ") VALUES(?, ?)";

    public static final String QUERY_ACTOR_BY_NAME = "SELECT " + COLUMN_ACTOR_ID + ", " + COLUMN_ACTOR_FULLNAME + ", " + COLUMN_ACTOR_DATE_OF_BIRTH + " FROM " + TABLE_ACTORS +
            " WHERE " + COLUMN_ACTOR_FULLNAME + " = ?";

    public static final String QUERY_ACTOR_BY_ID = "SELECT " + COLUMN_ACTOR_ID + ", " + COLUMN_ACTOR_FULLNAME + ", " + COLUMN_ACTOR_DATE_OF_BIRTH + " FROM " + TABLE_ACTORS +
            " WHERE " + COLUMN_ACTOR_ID + " = ?";

    public static final String QUERY_ACTOR_BY_FILM_ID = "SELECT " + COLUMN_ACTOR_ID + ", " + COLUMN_ACTOR_FULLNAME + ", " + COLUMN_ACTOR_DATE_OF_BIRTH + " FROM " + TABLE_ACTORS +
            " JOIN " + TABLE_IDS + " ON " + TABLE_ACTORS + "." + COLUMN_ACTOR_ID + " = " + COLUMN_IDS_ACTOR +
            " WHERE " + COLUMN_IDS_FILM + " = ?";

    public static final String UPDATE_ACTOR = "UPDATE " + TABLE_ACTORS + " SET " + COLUMN_ACTOR_FULLNAME + " = ?" + ", " + COLUMN_ACTOR_DATE_OF_BIRTH + " = ?" +
            " WHERE " + COLUMN_ACTOR_ID + " = ?";

    public static final String DELETE_ACTOR = "DELETE FROM " + TABLE_ACTORS + " WHERE " + COLUMN_ACTOR_ID + " = ?";

    public static final String COUNT_FILMS_OF_ACTOR = "SELECT " + TABLE_IDS + "." + COLUMN_IDS_ACTOR + ", "
            + " COUNT (*) FROM " + TABLE_IDS +
            " WHERE " + TABLE_IDS + "." + COLUMN_IDS_ACTOR + " = ?" +
            " GROUP BY " + TABLE_IDS + "." + COLUMN_IDS_ACTOR;

    public static final String ACTOR_IS_DIRECTOR = "SELECT " + TABLE_ACTORS + "." + COLUMN_ACTOR_ID + ", " + TABLE_ACTORS + "." + COLUMN_ACTOR_FULLNAME + ", " +
            TABLE_ACTORS + "." + COLUMN_ACTOR_DATE_OF_BIRTH +
            " FROM " + TABLE_ACTORS +
            " INNER JOIN " + TABLE_IDS + " ON " + TABLE_IDS + "." + COLUMN_IDS_ACTOR + " = " + TABLE_ACTORS + "." + COLUMN_ACTOR_ID +
            " INNER JOIN " + TABLE_FILMS + " ON " + TABLE_FILMS + "." + COLUMN_FILM_ID + " = " + TABLE_IDS + "." + COLUMN_IDS_FILM +
            " INNER JOIN " + TABLE_DIRECTORS + " ON " + TABLE_DIRECTORS + "." + COLUMN_DIRECTOR_ID + " = " + TABLE_FILMS + "." + COLUMN_FILM_DIRECTOR_ID +
            " WHERE " + TABLE_ACTORS + "." + COLUMN_ACTOR_FULLNAME + " = " + TABLE_DIRECTORS + "." + COLUMN_DIRECTOR_FULLNAME +
            " AND " + TABLE_ACTORS + "." + COLUMN_ACTOR_DATE_OF_BIRTH + " = " + TABLE_DIRECTORS + "." + COLUMN_DIRECTOR_DATE_OF_BIRTH;


    public ActorRepository(Connection conn) {
        try {
            this.conn = conn;

            insertIntoActors = conn.prepareStatement(INSERT_ACTOR);
            queryActorByName = conn.prepareStatement(QUERY_ACTOR_BY_NAME);
            queryActorByID = conn.prepareStatement(QUERY_ACTOR_BY_ID);
            queryActorByFilmID = conn.prepareStatement(QUERY_ACTOR_BY_FILM_ID);
            updateActor = conn.prepareStatement(UPDATE_ACTOR);
            deleteActor = conn.prepareStatement(DELETE_ACTOR);
            countFilms = conn.prepareStatement(COUNT_FILMS_OF_ACTOR);

        } catch (SQLException e) {
            System.out.println("ActorRepository couldn't connect to database: " + e.getMessage());

        }
    }

    public void closeStatements() {
        try {
            if (insertIntoActors != null) {
                insertIntoActors.close();
            }
            if (queryActorByName != null) {
                queryActorByName.close();
            }
            if (queryActorByID != null) {
                queryActorByID.close();
            }
            if (queryActorByFilmID != null) {
                queryActorByFilmID.close();
            }
            if (updateActor != null) {
                updateActor.close();
            }
            if (deleteActor != null) {
                deleteActor.close();
            }
            if (countFilms != null) {
                countFilms.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close preparedStatements: " + e.getMessage());
        }
    }


    public Actor createActor(Actor actor) throws SQLException {
        queryActorByName.setString(1, actor.getFullName());

        ResultSet results = queryActorByName.executeQuery();
        if (results.next()) {
            throw new SQLException("Actor already exists");
        } else {
            insertIntoActors.setString(1, actor.getFullName());
            insertIntoActors.setString(2, actor.getDob());
            int affectedRows = insertIntoActors.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert actor!");
            }
            ResultSet generatedKeys = insertIntoActors.getGeneratedKeys();
            if (generatedKeys.next()) {
                actor.setId(generatedKeys.getInt(INDEX_ACTOR_ID));
                return actor;
            } else {
                throw new SQLException("Couldn't get _id for actor");
            }
        }
    }

    public Actor getActor(int id) {
        try {
            queryActorByID.setInt(1, id);
            ResultSet results = queryActorByID.executeQuery();
            if (results.next()) {
                Actor actor = new Actor();
                actor.setId(results.getInt(INDEX_ACTOR_ID));
                actor.setFullName(results.getString(INDEX_ACTOR_FULLNAME));
                actor.setDob(results.getString(INDEX_ACTOR_DATE_OF_BIRTH));
                return actor;
            }
        } catch (SQLException e) {
            System.out.println("Actor's id: " + id + "doesn't exist" + e.getMessage());
        }
        return null;
    }

    public void updateActor(@NotNull Actor actor) {
        try {

            updateActor.setString(1, actor.getFullName());
            updateActor.setString(2, actor.getDob());
            updateActor.setInt(3, actor.getId());

            updateActor.execute();
        } catch (SQLException e) {
            System.out.println("Update actor: " + actor.getFullName() + " failed" + e.getMessage());
        }
    }

    public void deleteActor(int id) {
        try {
            deleteActor.setInt(1, id);
            deleteActor.execute();

        } catch (SQLException e) {
            System.out.println("Query DELETE ACTOR failed: " + e.getMessage());

        }

    }

    public List<Actor> queryActors() {

        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_ACTORS);
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            return getActors(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return Collections.emptyList();
        }


    }


    public List<Actor> queryActorsByFilmID(int filmId) {
        try {
            queryActorByFilmID.setInt(1, filmId);
            ResultSet results = queryActorByFilmID.executeQuery();
            return getActors(results);

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return Collections.EMPTY_LIST;
        }


    }

    private int countFilms(int actorID) throws SQLException {
        countFilms.setInt(1, actorID);
        ResultSet results = countFilms.executeQuery();
        if (results.next()) {
            return results.getInt(2);
        }
        return 0;
    }


    public List<Actor> queryActorsInNFilms(int numberOfFilms) {
        List<Actor> filteredActors = new ArrayList<>();
        List<Actor> allActors = queryActors();

        for (int i = 0; i < allActors.size(); i++) {
            Actor currentActor = allActors.get(i);

            try {
                int count = countFilms(currentActor.getId());
                if (count >= numberOfFilms) {
                    filteredActors.add(currentActor);
                }
            } catch (SQLException e) {
                System.out.println("Query failed: " + e.getMessage());
                return null;
            }

        }
        return filteredActors;
    }

    public List<Actor> queryActorsDirectors() {
        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(ACTOR_IS_DIRECTOR)) {
            return getActors(results);
        } catch (SQLException e) {
            System.out.println("QueryActorsDirectors failed: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<Actor> getActors(ResultSet results) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        while (results.next()) {
            Actor actor = new Actor();
            actor.setId(results.getInt(INDEX_ACTOR_ID));
            actor.setFullName(results.getString(INDEX_ACTOR_FULLNAME));
            actor.setDob(results.getString(INDEX_ACTOR_DATE_OF_BIRTH));
            actors.add(actor);
        }
        return actors;
    }


}




