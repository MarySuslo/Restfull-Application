package org.example.db;


import org.example.Errors.DataBaseException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    final String[] PROPERTY_DATE = new String[3];

    public String[] getPropertyData() {

        Properties properties = new Properties();
        try (InputStream inFile = ConnectionManagerImpl.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }

        PROPERTY_DATE[0] = properties.getProperty("db.url");
        PROPERTY_DATE[1] = properties.getProperty("db.username");
        PROPERTY_DATE[2] = properties.getProperty("db.password");

        return PROPERTY_DATE;
    }

    @Override
    public Connection getConnection() throws SQLException {

        Connection connection = null;

        String[] propertyData = getPropertyData();

        String urlDB = propertyData[0];
        String username = propertyData[1];
        String password = propertyData[2];

        DriverManager.registerDriver(new org.postgresql.Driver());

        connection = DriverManager.getConnection(urlDB, username, password);

        return connection;
    }
}
