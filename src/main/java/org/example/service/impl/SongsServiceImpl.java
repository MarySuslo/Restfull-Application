package org.example.service.impl;

import org.example.model.Songs;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.SongsService;

public class SongsServiceImpl implements SongsService {

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

    @Override
    public boolean update(Songs song) {
        return songsRepository.update(song);
    }

    @Override
    public boolean delete(int id) {
        return songsRepository.deleteById(id);
    }

}
