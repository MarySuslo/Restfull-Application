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
import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.;
import org.testcontainers.containers.*;

import org.testcontainers.junit.jupiter.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SongsRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("Music")
                    .withUsername("postgres")
                    .withInitScript("db-migration.SQL")
                    .withPassword("111");

    @Test
    void findById() {

        int id = 1;
        Songs song = new SongsRepositoryImpl().findById(id);
        String resultSong = "";
        //Connection conn=DriverManager.getConnection(container.getJdbcUrl(),container.getUsername(),container.getPassword());

        ConnectionManager connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs " +
                    "where id_song=" + id + " order by id_song");
            ResultSet result = prStatement.executeQuery();
            while (result.next()) {
                int songId = result.getInt("id_song");
                String nameSong = result.getString("name_song");

                Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));

                resultSong = (new Songs(songId, nameSong, singer)).toString();
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        assertEquals(resultSong, song.toString());

        try {
            new SongsRepositoryImpl().findById(9);
        } catch (NotFoundException e) {
            assertEquals("Песни с таким индексон не найдено", e.getMessage());
        }
    }

    @Test
    void findAll() {

        List<Songs> songs = new SongsRepositoryImpl().findAll();
        List<String> findSongs = new ArrayList<>();

        ConnectionManager connectionManager = new ConnectionManagerImpl();

        List<Songs> songsList = new ArrayList<Songs>();

        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs order by id_song");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_song");
                String nameSong = result.getString("name_song");

                Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));

                findSongs.add((new Songs(id, nameSong, singer)).toString());
            }
        } catch (SQLException e) {
            throw new NotFoundException("Песни не найдено");
        }

        assertEquals(findSongs, songs.stream().map(Songs::toString).collect(Collectors.toList()));

    }

    @Test
    void save() {
        Singers singer = new SingersRepositoryImpl().findById(2);
        Songs song = new Songs(4, "Rapunzel", singer);
        new SongsRepositoryImpl().save(song);
        Songs savedSong = new SongsRepositoryImpl().findById(4);
        assertEquals(savedSong.toString(), song.toString());

        Songs songsError = new Songs(2, "Rapunzel", singer);
        assertThrows(DuplicateDataException.class, () -> {
            new SongsRepositoryImpl().save(songsError);
        });
    }

    @Test
    void deleteById() {

        assertTrue(new SongsRepositoryImpl().deleteById(4));

        assertThrows(DataBaseException.class, () -> {
            new SongsRepositoryImpl().deleteById(-8);
        });

    }

    @Test
    void update() {
        Singers singer = new SingersRepositoryImpl().findById(1);
        Songs song = new Songs(2, "Good pirate", singer);
        new SongsRepositoryImpl().update(song);
        Songs updateSong = new SongsRepositoryImpl().findById(2);
        assertEquals(updateSong.toString(), song.toString());

        song.setNameSong("Wild grasses");
        song.setSinger(new SingersRepositoryImpl().findById(2));

        new SongsRepositoryImpl().update(song);
        updateSong = new SongsRepositoryImpl().findById(2);
        assertEquals(updateSong.toString(), song.toString());

        assertThrows(DataBaseException.class, () -> {
            new SongsRepositoryImpl().update(new Songs(8, "HAE", singer));
        });

    }

}
