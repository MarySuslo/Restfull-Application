package org.example;

import org.example.Errors.DataBaseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseContext {
    public static void init(Connection conn) {
        String script = null;
        try {
            script = new String(Files.readAllBytes(Paths.get("./src/main/resources/db_migration.sql")));
        } catch (IOException e) {
            throw new DataBaseException("Script not found");
        }

        try (Statement statement = conn.createStatement()) {
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
