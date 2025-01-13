package org.example.servlet.mapper;

import org.example.model.Singers;
import org.example.servlet.dto.SingersDto;

public class SingersDtomapperImpl implements SimpleDtomapper<Singers, SingersDto> {

    @Override
    public Singers mapFromDto(SingersDto singerDto) {
        Singers singer = new Singers(singerDto.getIdSinger(), singerDto.getNameSinger());
        singer.setSongs(singerDto.getSongs());
        return singer;
    }

    @Override
    public SingersDto mapToDto(Singers singer) {
        SingersDto singersDto = new SingersDto(singer.getIdSinger(), singer.getNameSinger());
        singersDto.setSongs(singer.getSongs());
        return singersDto;
    }
}
