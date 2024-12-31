package org.example.servlet.mapper;

import org.example.model.Singers;
import org.example.servlet.dto.SingersDto;

public class SingersDtomapperImpl implements SimpleDtomapper<Singers, SingersDto> {

    @Override
    public Singers mapFromDto(SingersDto singerDto) {
        return new Singers(singerDto.getIdSinger(), singerDto.getNameSinger());
    }

    @Override
    public SingersDto mapToDto(Singers singer) {
        return new SingersDto(singer.getIdSinger(), singer.getNameSinger());
    }
}
