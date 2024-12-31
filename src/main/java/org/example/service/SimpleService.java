package org.example.service;


public interface SimpleService<T> {

    T findById(int id);

    boolean save(T song);

}

