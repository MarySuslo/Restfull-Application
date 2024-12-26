package org.example.servlet.mapper;

import org.example.model.Songs;
import org.example.servlet.dto.SongsDto;

public class SimpleDtomapperImpl implements SimpleDtomapper {


    @Override
    public Songs map(SongsDto songDto) {
        Songs song = new Songs(songDto.getIdSong(), songDto.getNameSong(), songDto.getSinger());
        return song;
    }

    @Override
    public SongsDto map(Songs song) {
        SongsDto songsDto = new SongsDto(song.getIdSong(), song.getNameSong(), song.getSinger());
        return songsDto;
    }
}
