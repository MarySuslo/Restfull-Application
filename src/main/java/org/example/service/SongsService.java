package org.example.service;


import org.example.model.Songs;

public interface SongsService {

    Songs findById(int id);

    boolean save(Songs song);

    boolean update(Songs song);

    boolean delete(int id);

}

