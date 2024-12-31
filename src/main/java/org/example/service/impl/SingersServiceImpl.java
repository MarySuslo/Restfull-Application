package org.example.service.impl;

import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.example.service.SimpleService;


public class SingersServiceImpl implements SimpleService<Singers> {

    private final SingersRepositoryImpl singerRepository;

    public SingersServiceImpl(SingersRepositoryImpl singersRepository) {
        this.singerRepository = singersRepository;
    }

    @Override
    public Singers findById(int id) {
        return singerRepository.findById(id);
    }

    @Override
    public boolean save(Singers singer) {
        return singerRepository.save(singer);
    }

}
