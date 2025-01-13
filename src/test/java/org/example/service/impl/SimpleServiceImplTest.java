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

class SimpleServiceImplTest {

    @Mock
    private SongsRepositoryImpl songsRepository;

    @Mock
    private SingersRepositoryImpl singersRepository;

    @InjectMocks
    private SongsServiceImpl songService;

    @InjectMocks
    private SingersServiceImpl singerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findSongById() {

        int id = 2;
        String nameSong = "Withards doll";

        Singers singer = new Singers(1, "KingAndJocker");

        Songs song = new Songs(id, nameSong, singer);

        when(songsRepository.findById(id)).thenReturn(song);

        Songs findSong = songService.findById(id);

        assertNotNull(findSong);
        assertEquals(findSong.toString(), song.toString());

        when(songsRepository.findById(8)).thenThrow(new NotFoundException("Песни с таким индексон не найдено"));

        assertThrows(NotFoundException.class, () -> songService.findById(8));

    }

    @Test
    void saveSong() {

        int songId = 1;
        String songName = "Confession of a Vampire";
        Singers singer = new Singers(1, "KingAndJocker");
        Songs song = new Songs(songId, songName, singer);

        when(songService.save(song)).thenReturn(true);

        songService.save(song);

        verify(songsRepository, times(1)).save(song);

        when(songsRepository.findById(songId)).thenReturn(song);

        Songs resultSong = songsRepository.findById(songId);

        assertNotNull(resultSong, "Песня должна быть сохранена");
        assertEquals(resultSong.toString(), song.toString());
    }

    @Test
    void findSingerById() {

        int id = 1;

        String nameSinger = "KingAndJocker";

        Singers singer = new Singers(id, nameSinger);

        when(singersRepository.findById(id)).thenReturn(singer);

        Singers findSinger = singerService.findById(id);

        assertNotNull(findSinger);
        assertEquals(findSinger.toString(), singer.toString());

        when(singersRepository.findById(8)).thenThrow(
                new NotFoundException("Исполнителя с таким индексон не найдено"));

        assertThrows(NotFoundException.class, () -> singerService.findById(8));

    }

    @Test
    void saveSinger() {

        int id = 1;

        String nameSinger = "KingAndJocker";

        Singers singer = new Singers(id, nameSinger);

        when(singerService.save(singer)).thenReturn(true);

        singerService.save(singer);

        verify(singersRepository, times(1)).save(singer);

        when(singersRepository.findById(id)).thenReturn(singer);

        Singers resultSinger = singersRepository.findById(id);

        assertNotNull(resultSinger, "Песня должна быть сохранена");
        assertEquals(resultSinger.toString(), singer.toString());
    }
}
