package org.example.repository.impl;

import org.example.Errors.DataBaseException;
import org.example.Errors.DuplicateDataException;
import org.example.Errors.NotFoundException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SingersRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"))

            .withDatabaseName("music")
            .withUsername("postgres")
            .withPassword("111")
            .withInitScript("db_migration.sql");

    @BeforeAll
    public static void setUp(){
        container.start();
    }

    @Test
    void findById() {
        int id = 1;
        Singers singer = new SingersRepositoryImpl().findById(id);

        String resultSinger = "Singer: {\n id: 1;\n name: KingAndJocker;\n}";

        assertEquals(resultSinger, singer.toString());

        try {
            new SingersRepositoryImpl().findById(27);
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
        Singers singer = new Singers(8, "Splin");

        new SingersRepositoryImpl().save(singer);
        Singers result = new SingersRepositoryImpl().findById(8);

        assertEquals(singer.toString(), result.toString());
        Singers singerError = new Singers(8, "Splin");
        assertThrows(DuplicateDataException.class, () -> {
            new SingersRepositoryImpl().save(singerError);
        });
    }

    @Test
    void deleteById() {

        assertTrue(new SingersRepositoryImpl().deleteById(5));
        assertFalse(new SingersRepositoryImpl().deleteById(27));
    }

    @Test
    void update() {
        Singers singer1 = new Singers(3, "Cinema");
        new SingersRepositoryImpl().update(singer1);
        Singers updateSinger1 = new SingersRepositoryImpl().findById(3);
        assertEquals(updateSinger1.toString(), singer1.toString());

        Singers singer2 = new SingersRepositoryImpl().findById(2);
        singer2.setNameSinger("Water mill");

        assertTrue(new SingersRepositoryImpl().update(singer2));

        Singers singer3 = new Singers(26, "TDG");

        assertThrows(DataBaseException.class, () -> {
            new SingersRepositoryImpl().update(singer3);
        });

    }

    @AfterAll
    public static void endDate() {
        container.stop();
    }
}
