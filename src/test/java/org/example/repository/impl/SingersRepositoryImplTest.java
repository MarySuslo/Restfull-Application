package org.example.repository.impl;

import org.example.Errors.DataBaseException;
import org.example.Errors.DuplicateDataException;
import org.example.Errors.NotFoundException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.*;

import org.testcontainers.junit.jupiter.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.sql.DriverManager.getConnection;
import static org.junit.jupiter.api.Assertions.*;

public class SingersRepositoryImplTest {

    //создаем здесь testcontainer
    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("Music")
                    .withUsername("postgres")
                    .withInitScript("db_migration.SQL")
                    .withPassword("111");
 /*       @BeforeAll
   public static void setUp() {

        container.start();
    }*/

    @Test
    void findById() {
        int id = 1;
        Singers singer = new SingersRepositoryImpl().findById(id);

        String resultSinger = "";

        ConnectionManager connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from singers " +
                    "where id_singer=" + id + " order by id_singer");
            ResultSet result = prStatement.executeQuery();
            while (result.next()) {
                int singerId = result.getInt("id_singer");
                String nameSinger = result.getString("name_singer");

                resultSinger = (new Singers(singerId, nameSinger)).toString();
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        assertEquals(resultSinger, singer.toString());

        try {
            new SingersRepositoryImpl().findById(7);
        } catch (NotFoundException e) {
            assertEquals("Исполнителя с таким индексон не найдено", e.getMessage());
        }

    }

    @Test
    void findAll() {
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
        } catch (SQLException e) {
            e.getMessage();
        }

        assertEquals(finfSingers, singers.stream().map(Singers::toString).collect(Collectors.toList()));

    }

    @Test
    void save() {
        Singers singer = new Singers(4, "Splin");
        assertTrue(new SingersRepositoryImpl().save(singer));

        Singers singerError = new Singers(3, "Splin");
        assertThrows(DuplicateDataException.class, () -> {
            new SingersRepositoryImpl().save(singerError);
        });
    }

    @Test
    void deleteById() {

        assertTrue(new SingersRepositoryImpl().deleteById(4));

        try {
            new SingersRepositoryImpl().deleteById(7);
        } catch (DataBaseException e) {
            assertEquals("Ошибка удаления", e.getMessage());
        }
    }

    @Test
    void update() {
        Singers singer1 = new Singers(3, "Cinema");
        new SingersRepositoryImpl().update(singer1);
        Singers updateSinger1 = new SingersRepositoryImpl().findById(3);
        assertEquals(updateSinger1.toString(), singer1.toString());

        Singers singer2 = new SingersRepositoryImpl().findById(1);
        singer2.setNameSinger("King of jockers");
        new SingersRepositoryImpl().update(singer2);
        Singers updateSinger2 = new SingersRepositoryImpl().findById(1);
        assertEquals(updateSinger2.toString(), singer2.toString());

        assertThrows(DataBaseException.class, () -> {
            new SingersRepositoryImpl().update(new Singers(8, "TDG"));
        });

    }
}
