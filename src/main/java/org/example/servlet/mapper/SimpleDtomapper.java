package org.example.servlet.mapper;

import org.example.model.Songs;
import org.example.servlet.dto.SongsDto;

public interface SimpleDtomapper {
    Songs map(SongsDto incomingDto);

    SongsDto map(Songs simpleEntity);
}
