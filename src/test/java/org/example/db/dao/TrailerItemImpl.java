package org.example.db.dao;

import org.example.db.ConnectionFactory;
import org.example.models.newTrailers.TrailerItem;

import java.sql.*;
import java.util.List;
import java.util.Set;

public class TrailerItemImpl implements TrailerItemDao {
    Connection connection = ConnectionFactory.getConnection();

    @Override
    public TrailerItem getTrailer(long id) {
        TrailerItem trailer = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM TrailerItems WHERE id = " + id
            );
            if (rs.next()) {
                trailer = extractTrailerItem(rs);
            }
            connection.close();
            return trailer;
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Set<TrailerItem> getAllTrailers() {
        return null;
    }

    @Override
    public boolean insertTrailer(TrailerItem trailerItem) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO TrailerItems (film_id, genre, year_row, title) " +
                            "VALUES (?, ? , ?, ?)"
            );
            statement.setLong(1, trailerItem.getFilmId());
            statement.setString(2, trailerItem.getGenre());
            statement.setString(3, trailerItem.getYear());
            statement.setString(4, trailerItem.getTitle());

            int rs = statement.executeUpdate();
            if (rs > 0) {
                System.out.println("Инфа вставлена в БД " + trailerItem);
            } else {
                System.out.println("Инфа НЕ вставлена в БД " + trailerItem);
            }
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateTrailer() {
        return false;
    }

    @Override
    public boolean deleteTrailer() {
        return false;
    }

    @Override
    public boolean executeTransaction(Runnable runnable) {
        try {
            connection.setAutoCommit(false);
            runnable.run();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean insertTrailers(List<TrailerItem> trailers) {
        executeTransaction(
                () -> {
                    trailers.forEach(trailer ->
                            {
                                try {
                                    extractStatementBy(trailer).executeUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        return false;
    }

    private TrailerItem extractTrailerItem(ResultSet rs) throws SQLException {
        TrailerItem trailer = new TrailerItem();
        trailer.setId(rs.getLong("id"));
        trailer.setFilmId(rs.getLong("film_id"));
        trailer.setGenre(rs.getString("genre"));
        trailer.setTitle(rs.getString("title"));
        trailer.setYear(rs.getString("year_row"));
        return trailer;
    }

    private PreparedStatement extractStatementBy(TrailerItem trailerItem) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO TrailerItems (film_id, genre, year_row, title, link) " +
                            "VALUES (?, ? , ?, ?, ?)"
            );
            statement.setLong(1, trailerItem.getFilmId());
            statement.setString(2, trailerItem.getGenre());
            statement.setString(3, trailerItem.getYear());
            statement.setString(4, trailerItem.getTitle());
            statement.setString(5, trailerItem.getLink());
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
