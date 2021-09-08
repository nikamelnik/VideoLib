package com.company.dbModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorRepository {


    public static final String TABLE_DIRECTORS = "directors";
    public static final String COLUMN_DIRECTOR_ID = "_id";
    public static final String COLUMN_DIRECTOR_FULLNAME = "full_name";
    public static final String COLUMN_DIRECTOR_DATE_OF_BIRTH = "date_of_birth";
    public static final int INDEX_DIRECTOR_ID = 1;
    public static final int INDEX_DIRECTOR_FULLNAME = 2;
    public static final int INDEX_DIRECTOR_DATE_OF_BIRTH = 3;

    private PreparedStatement queryDirectorByName;
    private PreparedStatement queryDirectorById;

    private PreparedStatement insertIntoDirectors;
    private PreparedStatement updateDirector;
    private PreparedStatement deleteDirector;

    public static final String INSERT_DIRECTOR = "INSERT INTO " + TABLE_DIRECTORS +
            '(' + COLUMN_DIRECTOR_FULLNAME + ", " + COLUMN_DIRECTOR_DATE_OF_BIRTH + ") VALUES(?, ?)";
    public static final String QUERY_DIRECTOR_BY_NAME = "SELECT " + COLUMN_DIRECTOR_ID + ", " + COLUMN_DIRECTOR_FULLNAME + ", " + COLUMN_DIRECTOR_DATE_OF_BIRTH + " FROM " + TABLE_DIRECTORS +
            " WHERE " + COLUMN_DIRECTOR_FULLNAME + " = ?";
    public static final String QUERY_DIRECTOR_BY_ID = "SELECT " + COLUMN_DIRECTOR_ID + ", " + COLUMN_DIRECTOR_FULLNAME + ", " + COLUMN_DIRECTOR_DATE_OF_BIRTH + " FROM " + TABLE_DIRECTORS +
            " WHERE " + COLUMN_DIRECTOR_ID + " = ?";
    public static final String UPDATE_DIRECTOR = "UPDATE " + TABLE_DIRECTORS + " SET " + COLUMN_DIRECTOR_FULLNAME + " = ?" + ", " + COLUMN_DIRECTOR_DATE_OF_BIRTH + " = ?" +
            " WHERE " + COLUMN_DIRECTOR_ID + " = ?";
    public static final String DELETE_DIRECTOR = "DELETE FROM " + TABLE_DIRECTORS + " WHERE " + COLUMN_DIRECTOR_ID + " = ?";


    public DirectorRepository(Connection conn) {
        try {
            insertIntoDirectors = conn.prepareStatement(INSERT_DIRECTOR);
            queryDirectorByName = conn.prepareStatement(QUERY_DIRECTOR_BY_NAME);
            queryDirectorById = conn.prepareStatement(QUERY_DIRECTOR_BY_ID);
            updateDirector = conn.prepareStatement(UPDATE_DIRECTOR);
            deleteDirector = conn.prepareStatement(DELETE_DIRECTOR);
        } catch (SQLException e) {
            System.out.println("DirectorRepository couldn't connect to database: " + e.getMessage());

        }
    }

    public void closeStatements(Connection conn) {
        try {
            if (insertIntoDirectors != null) {
                insertIntoDirectors.close();
            }
            if (queryDirectorByName != null) {
                queryDirectorByName.close();
            }
            if (queryDirectorById != null) {
                queryDirectorById.close();
            }
            if (updateDirector != null) {
                updateDirector.close();
            }
            if (deleteDirector != null) {
                deleteDirector.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close preparedStatements: " + e.getMessage());
        }
    }

    public Director createDirector(Director director) throws SQLException {
        queryDirectorByName.setString(1, director.getFullName());

        ResultSet results = queryDirectorByName.executeQuery();
        if (results.next()) {
            throw new SQLException("Director already exists");
        } else {
            insertIntoDirectors.setString(1, director.getFullName());
            insertIntoDirectors.setString(2, director.getDob());
            int affectedRows = insertIntoDirectors.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert director!");
            }
            ResultSet generatedKeys = insertIntoDirectors.getGeneratedKeys();
            if (generatedKeys.next()) {
                director.setId(generatedKeys.getInt(1));
                return director;
            } else {
                throw new SQLException("Couldn't get _id for director");
            }
        }
    }


    public Director getDirector(int id) {
        try {
            queryDirectorById.setInt(1, id);
            ResultSet results = queryDirectorById.executeQuery();
            if (results.next()) {
                Director director = new Director();
                director.setId(results.getInt(INDEX_DIRECTOR_ID));
                director.setFullName(results.getString(INDEX_DIRECTOR_FULLNAME));
                director.setDob(results.getString(INDEX_DIRECTOR_DATE_OF_BIRTH));
                return director;
            }
        } catch (SQLException e) {
            System.out.println("Director's id: " + id + "doesn't exist" + e.getMessage());
        }
        return null;
    }

    public void updateDirector(Director director, String newName, String dateOfBirth) {
        try {
            updateDirector.setInt(3, director.getId());
            updateDirector.execute();
            updateDirector.setString(1, newName);
            updateDirector.setString(2, dateOfBirth);
        } catch (SQLException e) {
            System.out.println("Update actor: " + director.getFullName() + " failed" + e.getMessage());
        }
    }

    public void deleteDirector(int id) {
        try {
            deleteDirector.setInt(1, id);
            deleteDirector.execute();

        } catch (SQLException e) {
            System.out.println("Query DELETE DIRECTOR failed: " + e.getMessage());

        }

    }


    public List<Director> queryDirectors(Connection conn) {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_DIRECTORS);

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {
            List<Director> directors = new ArrayList<>();
            while (results.next()) {
                Director director = new Director();
                director.setId(results.getInt(INDEX_DIRECTOR_ID));
                director.setFullName(results.getString(INDEX_DIRECTOR_FULLNAME));
                director.setDob(results.getString(INDEX_DIRECTOR_DATE_OF_BIRTH));
                directors.add(director);
            }
            return directors;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }


    }
}