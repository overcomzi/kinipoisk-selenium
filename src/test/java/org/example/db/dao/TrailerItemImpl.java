package org.example.db.dao;

import org.apache.commons.dbutils.DbUtils;
import org.example.db.ConnectionFactory;
import org.example.models.newTrailers.TrailerItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TrailerItemImpl implements TrailerItemDao {
    private Connection currentConnection;

    @Override
    public TrailerItem getTrailer(long id) {
        Connection connection = null;
        TrailerItem trailer = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM TrailerItems WHERE id = ?");
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                trailer = extractTrailerItem(rs);
            }
            return trailer;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }

        return null;
    }


    @Override
    public boolean insertTrailer(TrailerItem trailerItem) {
        Connection connection = null;
        TrailerItem trailer = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = getTrailerInsertStatement(connection, trailerItem);
            int ok = statement.executeUpdate();

            if (ok > 0) {
                System.out.println("Трейлер вставлен в БД " + trailerItem);
            } else {
                System.out.println("Трейлер НЕ вставлен в БД " + trailerItem);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }

        return false;
    }

    @Override
    public boolean executeTransaction(Runnable runnable) {
        Connection connection = currentConnection;
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
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtils.closeQuietly(connection);
        }
        return true;
    }

    @Override
    public boolean insertTrailers(List<TrailerItem> trailers) {
        currentConnection = ConnectionFactory.getConnection();
        executeTransaction(
                () -> {
                    trailers.forEach(trailer ->
                            {
                                try {
                                    getTrailerInsertStatement(currentConnection, trailer).executeUpdate();
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

    private PreparedStatement getTrailerInsertStatement(Connection connection, TrailerItem trailerItem) {
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
