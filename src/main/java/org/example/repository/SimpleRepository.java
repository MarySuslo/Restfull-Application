package org.example.repository;

import java.util.List;

public interface SimpleRepository<T> {
    T findById(int id);

    boolean deleteById(int id);

    List<T> findAll();

    boolean save(T t);

    List<T> update(T t);
}

