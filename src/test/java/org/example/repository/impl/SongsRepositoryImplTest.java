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
    void save() {

        Singers singer= new Singers(2,"Windmill");
        Songs song = new Songs(4, "Rapunzel", singer);

        new SongsRepositoryImpl().save(song);
        Songs savedSong = new SongsRepositoryImpl().findById(4);

        assertEquals(savedSong.toString(), song.toString());
        Songs songsError = new Songs(4, "Rapunzel", singer);
        assertThrows(DuplicateDataException.class, () -> {
            new SongsRepositoryImpl().save(songsError);
        });
    }

    @Test
    void findById() {
        int id = 3;
        String songName = "Star of name Sun";
        Singers singer = new Singers(3,"Movie");
        Songs song = new Songs(id, songName, singer);
        new SingersRepositoryImpl().save(singer);
        new SongsRepositoryImpl().save(song);
        String resultSong = new SongsRepositoryImpl().findById(3).toString();

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

        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs order by id_song");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id =  result.getInt("id_song");
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
    void deleteById() {

        assertTrue(new SongsRepositoryImpl().deleteById(4));

        try {
            new SongsRepositoryImpl().deleteById(-8);
        } catch (DataBaseException e) {
            assertEquals("Ошибка удаления", e.getMessage());
        }
    }

    @Test
    void update() {

        Singers singer = new Singers(1,"KingAndJocker");
        Singers newSinger = new Singers(2,"Windmill");
       SingersRepositoryImpl singersRepository=new SingersRepositoryImpl();
        singersRepository.save(newSinger);
        singersRepository.save(singer);
        int songId1 = 1;
        String nameSong1 = "Confession of a Vampire";
        Songs song1 = new Songs(songId1, nameSong1, singer);
        int songId2 = 2;
        String nameSong2 = "Withards doll";
        Songs song2 = new Songs(songId2, nameSong2, singer);
        SongsRepositoryImpl songsRepository=new SongsRepositoryImpl();
        songsRepository.save(song1);
        songsRepository.save(song2);


        Songs song = new Songs(2, "Good pirate", singersRepository.findById(1));
        songsRepository.update(song);

        Songs updateSong = songsRepository.findById(2);
        assertEquals(updateSong.toString(), song.toString());

        song.setNameSong("Wild grasses");

        song.setSinger(newSinger);

        songsRepository.update(song);
        updateSong = songsRepository.findById(2);
        assertEquals(updateSong.toString(), song.toString());

        try {
            new SongsRepositoryImpl().update(new Songs(8, "HAE", singer));
        } catch (DataBaseException e) {
            assertEquals("Ошибка обновления", e.getMessage());
        }

    }

}
