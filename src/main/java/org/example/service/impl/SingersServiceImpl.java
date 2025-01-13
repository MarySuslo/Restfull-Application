package org.example.service.impl;

import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.example.service.SingersService;


public class SingersServiceImpl implements SingersService {

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

    @Override
    public boolean update(Singers singer) {
        return singerRepository.update(singer);
    }

    @Override
    public boolean delete(int id) {
        return singerRepository.deleteById(id);
    }

}
