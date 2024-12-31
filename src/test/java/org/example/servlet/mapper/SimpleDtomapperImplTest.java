package org.example.servlet.mapper;

import org.example.model.Singers;
import org.example.model.Songs;
import org.example.servlet.dto.SingersDto;
import org.example.servlet.dto.SongsDto;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SimpleDtomapperImplTest {

    private final SimpleDtomapper<Songs, SongsDto> mapperSong = new SongsDtomapperImpl();
    private final SimpleDtomapper<Singers, SingersDto> mapperSinger = new SingersDtomapperImpl();

    @Test
    void fromMapSong() {

        int id = 3;
        String nameSong = "Star of name Sun";
        int singerId = 3;
        Singers singer = new Singers(singerId, "Movie");
        SongsDto songDto = new SongsDto(id, nameSong, singer);
        Songs song = mapperSong.mapFromDto(songDto);

        assertEquals(songDto.toString(), song.toString());

    }

    @Test
    void toMapSong() {
        int id = 2;
        String nameSong = "Withards doll";
        int singerId = 1;
        Singers singer = new Singers(singerId, "KingAndJocker");

        Songs song = new Songs(id, nameSong, singer);

        SongsDto resultSongDto = mapperSong.mapToDto(song);

        assertEquals(song.toString(), resultSongDto.toString());

        song = new Songs(id, "Rom", singer);

        resultSongDto.setNameSong(song.getNameSong());

        assertEquals(song.toString(), resultSongDto.toString());
    }

    @Test
    void fromMapSinger() {

        int singerId = 3;
        String singerName = "Movie";

        SingersDto singerDto = new SingersDto(singerId, singerName);

        Singers singer = mapperSinger.mapFromDto(singerDto);

        assertEquals(singerDto.toString(), singer.toString());
    }

    @Test
    void toMapSinger() {
        int singerId = 3;
        String singerName = "Movie";

        Singers singer = new Singers(singerId, singerName);

        SingersDto resultSingerDto = mapperSinger.mapToDto(singer);

        assertEquals(singer.toString(), resultSingerDto.toString());

        singer = new Singers(singerId, "AteeZ");

        resultSingerDto.setNameSinger(singer.getNameSinger());

        assertEquals(singer.toString(), resultSingerDto.toString());
    }
}