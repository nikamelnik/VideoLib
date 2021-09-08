package com.company.dbModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmsActorsRepository {

    public static final String TABLE_IDS = "films_and_actors_id";
    public static final String COLUMN_IDS_FILM = "film_id";
    public static final String COLUMN_IDS_ACTOR = "actor_id";

    private Connection conn;

    private PreparedStatement insertIntoIDs;
    private PreparedStatement queryId;
    private PreparedStatement deleteFilmId;
    private PreparedStatement queryFilmsByActor;


    public static final String INSERT_ID = "INSERT INTO " + TABLE_IDS +
            '(' + COLUMN_IDS_FILM + ", " + COLUMN_IDS_ACTOR + ") VALUES(?, ?)";


    public static final String QUERY_ID = "SELECT " + COLUMN_IDS_FILM + ", " + COLUMN_IDS_ACTOR + " FROM " + TABLE_IDS +
            " WHERE " + COLUMN_IDS_FILM + " = ?" + " AND " + COLUMN_IDS_ACTOR + " = ?";


    public static final String QUERY_FILM_ID_BY_ACTOR_ID = "SELECT " + COLUMN_IDS_FILM + " FROM " + TABLE_IDS +
            " WHERE " + COLUMN_IDS_ACTOR + " = ?";

    public static final String DELETE_FILM_ID = "DELETE FROM " + TABLE_IDS + " WHERE " + COLUMN_IDS_FILM + " = ?";


    public FilmsActorsRepository(Connection conn) {
        try {
            insertIntoIDs = conn.prepareStatement(INSERT_ID);
            queryId = conn.prepareStatement(QUERY_ID);
            deleteFilmId = conn.prepareStatement(DELETE_FILM_ID);
            queryFilmsByActor = conn.prepareStatement(QUERY_FILM_ID_BY_ACTOR_ID);
            this.conn = conn;

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());

        }
    }

    public void close() {
        try {

            if (insertIntoIDs != null) {
                insertIntoIDs.close();
            }
            if (queryId != null) {
                queryId.close();
            }
            if (queryFilmsByActor != null) {
                queryFilmsByActor.close();
            }
            if (deleteFilmId != null) {
                deleteFilmId.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public void insertID(int filmId, int actorId) throws SQLException {
        queryId.setInt(1, filmId);
        queryId.setInt(2, actorId);
        ResultSet results = queryId.executeQuery();
        if (results.next()) {
            System.out.println("ID already exist");
            ;
        } else {
            insertIntoIDs.setInt(1, filmId);
            insertIntoIDs.setInt(2, actorId);
            int affectedRows = insertIntoIDs.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert ID!");
            }

        }
    }

    public void updateActorID(int filmId, List<Integer> actorIDs) throws SQLException {

        deleteFilmId.setInt(1, filmId);
        deleteFilmId.execute();

        for (int actorId : actorIDs) {
            insertID(filmId, actorId);
        }
    }

    public List<Integer> queryFilmIdByActorId(int actorId) throws SQLException {
        queryFilmsByActor.setInt(1, actorId);
        ResultSet results = queryFilmsByActor.executeQuery();
        List <Integer> filmIds = new ArrayList<>();

        while (results.next()) {
            filmIds.add(results.getInt(1));

        }
        return filmIds;
    }

}
