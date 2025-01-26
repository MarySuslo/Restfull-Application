package org.example.db;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.Errors.DataBaseException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DataBaseContext {
    Connection connection;

    public void init(Connection conn) {
        connection = conn;

        if (!tableIsPresent())
            createTable();
    }

    public boolean tableIsPresent() {
        try (
                Statement statement = connection.createStatement()) {
            boolean singersIsPresent = false;
            ResultSet singers = statement.executeQuery("SELECT EXISTS (" +
                    " SELECT tablename " +
                    "   FROM pg_catalog.pg_tables" +
                    "   WHERE tablename = 'singers');");
            if (singers.next()) singersIsPresent = singers.getBoolean(1);

            boolean songsIsPresent = false;
            ResultSet songs = statement.executeQuery("SELECT EXISTS (" +
                    "SELECT tablename " +
                    "   FROM  pg_catalog.pg_tables" +
                    "   WHERE tablename = 'songs');");
            if (songs.next()) songsIsPresent = songs.getBoolean(1);

            return singersIsPresent && songsIsPresent;

        } catch (SQLException sqlException) {
            throw new DataBaseException("Check table failed ");
        }
    }

    public void createTable() {
        String script = null;
        try {//
            script = new String(Files.readAllBytes(Paths.get("src/main/resources/db_migration.sql")));
        } catch (IOException e) {
            throw new DataBaseException("Script not found");
        }

        try (Statement statement = connection.createStatement()) {
            for (String command : script.split(";")) {
                if (!command.trim().isEmpty()) {
                    statement.executeUpdate(command + ";");
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Tables not created");
        }
    }

}
