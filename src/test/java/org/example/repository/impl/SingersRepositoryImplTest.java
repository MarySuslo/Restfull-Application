package org.example.repository.impl;

import com.zaxxer.hikari.HikariConfig;
import org.example.Errors.DuplicateDataException;
import org.example.Errors.NotFoundException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SingersRepositoryImplTest {


    @Test
    void findById() {
        int id = 1;
        Singers singer = new SingersRepositoryImpl().findById(id);

        String resultSinger = "Singer: {\n id: 1;\n name: King of jockers;\n}";

        assertEquals(resultSinger, singer.toString());

        try {
            new SingersRepositoryImpl().findById(7);
        } catch (NotFoundException e) {
            assertEquals("Исполнителя с таким индексон не найдено", e.getMessage());
        }
    }

    @Test
    void findAll() throws SQLException {
        List<Singers> singers = new SingersRepositoryImpl().findAll();

        List<String> finfSingers = new ArrayList<>();

        ConnectionManager connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from singers order by id_singer");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_singer");
                String nameSinger = result.getString("name_singer");

                finfSingers.add((new Singers(id, nameSinger)).toString());
            }
        }


        assertEquals(finfSingers, singers.stream().map(Singers::toString).collect(Collectors.toList()));

    }

    @Test
    void save() {
        Singers singer = new Singers(4, "Splin");

        new SingersRepositoryImpl().save(singer);
        Singers result = new SingersRepositoryImpl().findById(4);

        assertEquals(singer.toString(), result.toString());
        Singers singerError = new Singers(4, "Splin");
        assertThrows(DuplicateDataException.class, () -> {
            new SingersRepositoryImpl().save(singerError);
        });
    }

    @Test
    void deleteById() {

        assertTrue(new SingersRepositoryImpl().deleteById(4));
        assertFalse(new SingersRepositoryImpl().deleteById(7));
    }

    @Test
    void update() {
        Singers singer1 = new Singers(3, "Cinema");
        new SingersRepositoryImpl().update(singer1);
        Singers updateSinger1 = new SingersRepositoryImpl().findById(3);
        assertEquals(updateSinger1.toString(), singer1.toString());

        Singers singer2 = new SingersRepositoryImpl().findById(1);
        singer2.setNameSinger("King of jockers");

        assertTrue(new SingersRepositoryImpl().update(singer2));

        Singers singer3 = new Singers(6, "TDG");

        assertFalse(new SingersRepositoryImpl().update(singer3));

    }

    @AfterAll
    public static void endDate() {

        ConnectionManager connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prSongStatement = connection.prepareStatement("truncate table songs");
            prSongStatement.executeUpdate();

            PreparedStatement prSingersStatement = connection.prepareStatement("truncate table singers cascade");
            prSingersStatement.executeUpdate();

        } catch (SQLException e) {
            e.getMessage();
        }

    }
}
