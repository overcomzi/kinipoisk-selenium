package org.example.db;

import org.example.config.ConfProperties;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String dbUrl = ConfProperties.getProperty("connection.url");
    private static final String user = ConfProperties.getProperty("connection.user");
    private static final String password = ConfProperties.getProperty("connection.password");
    private static final String dbDriverName = ConfProperties.getProperty("db.driver-name");

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(
                    (Driver) Class.forName(dbDriverName)
                            .getDeclaredConstructor()
                            .newInstance());
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to the database", e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
