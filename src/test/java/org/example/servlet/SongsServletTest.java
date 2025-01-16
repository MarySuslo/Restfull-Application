package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.impl.SingersServiceImpl;
import org.example.service.impl.SongsServiceImpl;
import org.example.servlet.dto.SingersDto;
import org.example.servlet.dto.SongsDto;
import org.example.servlet.mapper.SimpleDtomapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SongsServletTest {

    @Mock
    private SongsServiceImpl songsService;

    @Mock
    private SimpleDtomapper<Songs, SongsDto> songsMapper;

    @Mock
    private SingersRepositoryImpl singersRepository;

    @Mock
    private SongsRepositoryImpl songsRepository;

    @InjectMocks
    private SongsServlet songsServlet;

    private Gson gson;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        int id = 1;
        String nameSong = "Confession of a Vampire";
        Singers singer = new Singers(1, "King of jockers");
        Songs song = new Songs(id, nameSong, singer);
        SongsDto songDto = new SongsDto(id, nameSong, singer);

        when(songsService.findById(id)).thenReturn(song);
        when(songsMapper.mapToDto(song)).thenReturn(songDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doGet(request, response);

        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(songDto), sw.toString());
    }

    @Test
    void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSong")).thenReturn("4");
        when(request.getParameter("name")).thenReturn("Dugon");
        when(request.getParameter("idSinger")).thenReturn("1");

        int id = 4;
        String nameSong = "Dugon";
        int singerId = 1;
        String nameSinger = "King of jockers";
        Singers singer = new Singers(singerId, nameSinger);
        Songs song = new Songs(id, nameSong, singer);
        SongsDto songDto = new SongsDto(id, nameSong, singer);

        when(singersRepository.findById(singerId)).thenReturn(singer);
        when(songsMapper.mapFromDto(any(SongsDto.class))).thenReturn(song);
        when(songsService.findById(id)).thenReturn(song);
        when(songsMapper.mapToDto(song)).thenReturn(songDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doPost(request, response);
        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(songDto), sw.toString());

    }

    @Test
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSong")).thenReturn("2");
        when(request.getParameter("name")).thenReturn("Way of dream");
        when(request.getParameter("idSinger")).thenReturn("2");

        int id = 2;
        String nameSong = "Way of dream";
        int singerId = 2;
        String nameSinger = "Windmill";
        Singers singer = new Singers(singerId, nameSinger);
        Songs song = new Songs(id, nameSong, singer);
        SongsDto songDto = new SongsDto(id, nameSong, singer);

        when(singersRepository.findById(singerId)).thenReturn(singer);
        when(songsMapper.mapFromDto(any(SongsDto.class))).thenReturn(song);
        when(songsService.findById(id)).thenReturn(song);
        when(songsMapper.mapToDto(song)).thenReturn(songDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doPut(request, response);
        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(songDto), sw.toString());

    }

    @Test
    void doDelete()throws ServletException, IOException{
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("3");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doDelete(request, response);
        verify(response).setContentType("application/json");

        assertEquals("\"{\\\"message\\\": \\\"Song deleted successfully\\\"}\"", sw.toString());
    }

    @Test
    void doGetStart() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Songs song1 = new Songs(1, "Confession of a Vampire", new Singers(1, "King of jockers"));

        Songs song2 = new Songs(2, "Way of dream", new Singers(2, "Windmill"));

        Songs song3 = new Songs(4, "Dugon",new Singers(1, "King of jockers") );

        List<Songs> songs = Arrays.asList( song1,song2, song3);

        when(songsRepository.findAll()).thenReturn(songs);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(songs), sw.toString());
    }

}
