package org.example.servlet.mapper;

import org.example.model.Songs;
import org.example.servlet.dto.SongsDto;

public interface SimpleDtomapper<T, E> {
    T mapFromDto(E incomingDto);

    E mapToDto(T simpleEntity);
}
