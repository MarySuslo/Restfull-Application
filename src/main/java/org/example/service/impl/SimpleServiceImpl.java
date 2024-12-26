package org.example.service.impl;

import org.example.model.Songs;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.SimpleService;

public class SimpleServiceImpl implements SimpleService {

    @Override
    public Songs findById(int id) {
        Songs song = new SongsRepositoryImpl().findById(id);
        return song;
    }

    @Override
    public void saveSong(Songs song) {
        new SongsRepositoryImpl().save(song);
    }

}
