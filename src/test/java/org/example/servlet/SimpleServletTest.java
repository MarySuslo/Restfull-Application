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
class SimpleServletTest {

    @Mock
    private SongsServiceImpl songsService;

    @Mock
    private SingersServiceImpl singersService;

    @Mock
    private SimpleDtomapper<Songs, SongsDto> songsMapper;
    @Mock
    private SimpleDtomapper<Singers, SingersDto> singersMapper;

    @Mock
    private SingersRepositoryImpl singersRepository;

    @Mock
    private SongsRepositoryImpl songsRepository;

    @InjectMocks
    private SongsServlet songsServlet;

    @InjectMocks
    private SingersServlet singersServlet;

    private Gson gson;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    void doGetSongServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        int id = 1;
        String nameSong = "Confession of a Vampire";
        Singers singer = new Singers(1, "KingAndJocker");
        Songs song = new Songs(id, nameSong, singer);
        SongsDto songDto = new SongsDto(id, nameSong, singer);
        singersRepository.save(singer);
        songsRepository.save(song);

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
    void doPostSongServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSong")).thenReturn("5");
        when(request.getParameter("name")).thenReturn("Dugon");
        when(request.getParameter("idSinger")).thenReturn("1");

        int id = 5;
        String nameSong = "Dugon";
        int singerId = 1;
        String nameSinger = "KingAndJocker";
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
    void doGetStartSongServlet() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Songs song1 = new Songs(1, "Confession of a Vampire", new Singers(1, "KingAndJocker"));
        Songs song2 = new Songs(2, "Wild grasses", new Singers(2, "Windmill"));
        Songs song3 = new Songs(3, "Star of name Sun", new Singers(3, "Movie"));
        Songs song4 = new Songs(5, "Dugon", new Singers(1, "KingAndJocker"));

        List<Songs> songs = Arrays.asList(song1, song2, song3, song4);

        when(songsRepository.findAll()).thenReturn(songs);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(songs), sw.toString());
    }

    @Test
    void doGetSingerServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        int id = 1;
        String nameSinger = "KingAndJocker";
        Singers singer = new Singers(id, nameSinger);
        SingersDto singerDto = new SingersDto(id, nameSinger);
        singersRepository.save(singer);

        when(singersService.findById(id)).thenReturn(singer);
        when(singersMapper.mapToDto(singer)).thenReturn(singerDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doGet(request, response);

        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(singerDto), sw.toString());
    }

    @Test
    void doPostSingerServlet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSinger")).thenReturn("5");
        when(request.getParameter("name")).thenReturn("Stray Kids");

        int id = 5;
        String nameSinger = "Stray Kids";
        Singers singer = new Singers(id, nameSinger);
        SingersDto singerDto = new SingersDto(id, nameSinger);

        when(singersMapper.mapFromDto(any(SingersDto.class))).thenReturn(singer);
        when(singersService.findById(id)).thenReturn(singer);
        when(singersMapper.mapToDto(singer)).thenReturn(singerDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doPost(request, response);
        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(singerDto), sw.toString());

    }

    @Test
    void doGetStartSingerServlet() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Singers singer1 = new Singers(1, "KingAndJocker");
        Singers singer2 = new Singers(2, "Windmill");
        Singers singer3 = new Singers(3, "Movie");
        Singers singer4 = new Singers(5, "Stray Kids");

        List<Singers> singers = Arrays.asList(singer1, singer2, singer3, singer4);

        when(singersRepository.findAll()).thenReturn(singers);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(singers), sw.toString());
    }
}
