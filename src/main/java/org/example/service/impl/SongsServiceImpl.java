package org.example.service.impl;

import org.example.model.Songs;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.SimpleService;

public class SongsServiceImpl implements SimpleService<Songs> {

    private final SongsRepositoryImpl songsRepository;

    public SongsServiceImpl(SongsRepositoryImpl songsRepository) {
        this.songsRepository = songsRepository;
    }

    @Override
    public Songs findById(int id) {
        return songsRepository.findById(id);
    }

    @Override
    public boolean save(Songs song) {
        return songsRepository.save(song);
    }

}
