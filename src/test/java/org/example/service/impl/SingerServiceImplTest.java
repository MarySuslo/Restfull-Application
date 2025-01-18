package org.example.service.impl;

import org.example.Errors.NotFoundException;
import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SingerServiceImplTest {

    @Mock
    private SingersRepositoryImpl singersRepository;

    @InjectMocks
    private SingersServiceImpl singerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {

        int id = 1;

        String nameSinger = "KingAndJocker";

        Singers singer = new Singers(id, nameSinger);

        when(singersRepository.findById(id)).thenReturn(singer);

        Singers findSinger = singerService.findById(id);

        assertNotNull(findSinger);
        assertEquals(findSinger.toString(), singer.toString());

        when(singersRepository.findById(28)).thenThrow(
                new NotFoundException("Исполнителя с таким индексон не найдено"));

        assertThrows(NotFoundException.class, () -> singerService.findById(28));

    }

    @Test
    void save() {

        int id = 9;

        String nameSinger = "Aria";

        Singers singer = new Singers(id, nameSinger);

        when(singerService.save(singer)).thenReturn(true);

        singerService.save(singer);

        verify(singersRepository, times(1)).save(singer);

        when(singersRepository.findById(id)).thenReturn(singer);

        Singers resultSinger = singersRepository.findById(id);

        assertNotNull(resultSinger, "Исполнитель должна быть сохранена");
        assertEquals(resultSinger.toString(), singer.toString());
    }

    @Test
    void delete() {
        int id = 7;

        when(singersRepository.deleteById(id)).thenReturn(true);

        assertTrue(singerService.delete(id));

        assertFalse(singerService.delete(30));

    }

    @Test
    void update() {

        Singers singer = new Singers(6, "TDG");


        when(singerService.update(singer)).thenReturn(true);

        singerService.update(singer);

        verify(singersRepository, times(1)).update(singer);

        assertTrue(singerService.update(singer));
    }


}
