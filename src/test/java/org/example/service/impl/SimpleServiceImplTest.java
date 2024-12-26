package org.example.service.impl;

import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleServiceImplTest {
    //мокаем репозиторий и другие зависимости

    @Test
    void save() {
        Singers singer = new SingersRepositoryImpl().findById(3);
        Songs song = new Songs(5, "Group of blood", singer);

        new SimpleServiceImpl().saveSong(song);

        Songs resultSong = new SongsRepositoryImpl().findById(5);

        assertEquals(resultSong.toString(), song.toString());
    }

    @Test
    void findById() {
        Songs findSong = new SimpleServiceImpl().findById(3);

        int id = 3;
        String nameSong = "Star of name Sun";
        Singers singer = new SingersRepositoryImpl().findById(3);

        assertEquals("Song: {\n id: " + id + ";\n name: " + nameSong + ";\n singer: " + singer.getNameSinger() + ";\n}", findSong.toString());

    }
}
