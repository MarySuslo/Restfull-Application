package org.example.servlet.mapper;

import org.example.model.Songs;
import org.example.servlet.dto.SongsDto;

public class SongsDtomapperImpl implements SimpleDtomapper<Songs, SongsDto> {

    @Override
    public Songs mapFromDto(SongsDto songDto) {
        return new Songs(songDto.getIdSong(), songDto.getNameSong(), songDto.getSinger());
    }

    @Override
    public SongsDto mapToDto(Songs song) {
        return new SongsDto(song.getIdSong(), song.getNameSong(), song.getSinger());
    }
}
