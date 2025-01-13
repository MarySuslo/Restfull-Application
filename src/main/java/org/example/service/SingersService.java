package org.example.service;

import org.example.model.Singers;
import org.example.model.Songs;

public interface SingersService {
    Singers findById(int id);

    boolean save(Singers singer);

    boolean update(Singers singer);

    boolean delete(int id);
}
