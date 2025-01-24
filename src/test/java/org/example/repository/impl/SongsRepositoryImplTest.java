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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.*;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SongsRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:17.2"))
            .withDatabaseName("music")
            .withUsername("postgres")
            .withPassword("111");

    @BeforeAll
    public static void setUp(){
        container.start();
    }

    @Test
    void save() {

        Singers singer = new SingersRepositoryImpl().findById(2);
        Songs song = new Songs(12, "Rapunzel", singer);

        new SongsRepositoryImpl().save(song);
        Songs savedSong = new SongsRepositoryImpl().findById(12);

        assertEquals(savedSong.toString(), song.toString());
        Songs songsError = new Songs(12, "Rapunzel", singer);
        assertThrows(DuplicateDataException.class, () -> {
            new SongsRepositoryImpl().save(songsError);
        });
    }

    @Test
    void findById() {
        int id = 1;
        String songName = "Confession of a Vampire";
        Singers singer = new Singers(1, "KingAndJocker");
        Songs song = new Songs(id, songName, singer);

        String resultSong = new SongsRepositoryImpl().findById(1).toString();

        assertEquals(resultSong, song.toString());

        try {
            new SongsRepositoryImpl().findById(29);
        } catch (NotFoundException e) {
            assertEquals("Песни с таким индексон не найдено", e.getMessage());
        }
    }

    @Test
    void findAll() throws SQLException {

        List<Songs> songs = new SongsRepositoryImpl().findAll();
        List<String> findSongs = new ArrayList<>();

        ConnectionManager connectionManager = new ConnectionManagerImpl();

        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs order by id_song");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_song");
                String nameSong = result.getString("name_song");

                Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));

                findSongs.add((new Songs(id, nameSong, singer)).toString());
            }
        }

        assertEquals(findSongs, songs.stream().map(Songs::toString).collect(Collectors.toList()));

    }

    @Test
    void deleteById() {

        assertTrue(new SongsRepositoryImpl().deleteById(6));

        try {
            new SongsRepositoryImpl().deleteById(-8);
        } catch (DataBaseException e) {
            assertEquals("Ошибка удаления", e.getMessage());
        }
    }

    @Test
    void update() {

        Singers singer = new Singers(1, "KingAndJocker");
        Singers newSinger = new Singers(4, "Stray Kids");

        SingersRepositoryImpl singersRepository = new SingersRepositoryImpl();
        SongsRepositoryImpl songsRepository = new SongsRepositoryImpl();

        Songs song = new Songs(5, "Go Life", singersRepository.findById(4));
        songsRepository.update(song);

        Songs updateSong = songsRepository.findById(5);
        assertEquals(updateSong.toString(), song.toString());

        song.setNameSong("AllIn");

        song.setSinger(newSinger);

        songsRepository.update(song);
        updateSong = songsRepository.findById(5);
        assertEquals(updateSong.toString(), song.toString());

        try {
            new SongsRepositoryImpl().update(new Songs(28, "HAE", singer));
        } catch (DataBaseException e) {
            assertEquals("Ошибка обновления", e.getMessage());
        }

    }

    @AfterAll
    public static void endDate() {

        container.stop();
    }
}
