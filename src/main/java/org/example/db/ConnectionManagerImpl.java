package org.example.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    public String[] getPropertyData() {
        String[] propertyData = new String[3];
        Properties properties = new Properties();
        try (InputStream inFile = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }

        propertyData[0] = properties.getProperty("db.url");
        propertyData[1] = properties.getProperty("db.username");
        propertyData[2] = properties.getProperty("db.password");

        return propertyData;
    }

    @Override
    public Connection getConnection() {

        Connection connection = null;

        try {
            String[] propertyData = getPropertyData();

            String urlDB = propertyData[0];
            String username = propertyData[1];
            String password = propertyData[2];

            DriverManager.registerDriver(new org.postgresql.Driver());

            connection = DriverManager.getConnection(urlDB, username, password);

        } catch (SQLException e) {
            e.getMessage();
        }
        return connection;
    }
}
