package org.example.servlet.mapper;

import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.example.servlet.dto.SongsDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDtomapperImplTest {

    @Test
    void map() {

        SongsDto songDto = new SongsDto(1, "Withard's doll", new SingersRepositoryImpl().findById(1));
        Songs song = new SimpleDtomapperImpl().map(songDto);
        assertEquals(songDto.toString(), song.toString());
    }

    @Test
    void testMap() {
        Songs song = new SongsRepositoryImpl().findById(3);
        SongsDto resultSongDto = new SimpleDtomapperImpl().map(song);
        assertEquals(song.toString(), resultSongDto.toString());
    }
}