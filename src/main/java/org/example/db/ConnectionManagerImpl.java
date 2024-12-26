package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {
    @Override
    public Connection getConnection() {

        Connection connection = null;

        Properties properties = new Properties();
        try (InputStream inFile = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }

        try {

            String classDriver = properties.getProperty("db.driver-class-name");
            String urlDB = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            DriverManager.registerDriver(new org.postgresql.Driver());

            connection = DriverManager.getConnection(urlDB, username, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
