package org.example.service;

import org.example.model.Songs;

public interface SimpleService {

    Songs findById(int id);

    void saveSong(Songs song);

}

