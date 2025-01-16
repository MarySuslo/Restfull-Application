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

class SongServiceImplTest {

    @Mock
    private SongsRepositoryImpl songsRepository;

    @InjectMocks
    private SongsServiceImpl songService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {

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
    void save() {

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
    void deleteById() {

        int id = 3;

        when(songsRepository.deleteById(id)).thenReturn(true);

        assertTrue(songService.delete(id));

        assertFalse(songService.delete(10));

    }

    @Test
    void update() {

        Singers singer = new Singers(1, "KISH");
        Songs song = new Songs(2, "rom for peaple", singer);

        when(songService.update(song)).thenReturn(true);

        songService.update(song);

        verify(songsRepository, times(1)).update(song);

        assertTrue(songService.update(song));

    }
}
