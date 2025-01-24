package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.impl.SongsServiceImpl;
import org.example.servlet.dto.SongsDto;
import org.example.servlet.mapper.SimpleDtomapper;
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
import java.sql.DriverManager;
import java.sql.SQLException;
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

    @BeforeAll
    public static void startUpData() throws SQLException {
        String db = "jdbc:postgresql://localhost:5432/" + "music";
        String user = "postgres";
        String password = "111";
       new DataBaseContext().init(DriverManager.getConnection(db, user, password));
    }

    @BeforeEach
    public void setUp() {
        gson = new Gson();
    }

    @Test
    void doGet() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("11");

        int id = 11;
        String nameSong = "Thunderous";
        Singers singer = new Singers(4, "Stray Kids");
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

        when(request.getParameter("idSong")).thenReturn("15");
        when(request.getParameter("name")).thenReturn("Dugon");
        when(request.getParameter("idSinger")).thenReturn("1");

        int id = 15;
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
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSong")).thenReturn("17");
        when(request.getParameter("name")).thenReturn("Wind Of Change");
        when(request.getParameter("idSinger")).thenReturn("13");

        int id = 17;
        String nameSong = "Wind Of Change";
        int singerId = 13;
        String nameSinger = "Scorpions";
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
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("9");

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

        List<Songs> songs = Arrays.asList(
                new Songs(1, "Confession of a Vampire", new Singers(1, "KingAndJocker")),
                new Songs(2, "Way of dream", new Singers(2, "Water mill")),
                new Songs(3, "Star of name Sun", new Singers(3, "Cinema")),
                new Songs(4, "Rom", new Singers(1, "KingAndJocker")),
                new Songs(5, "AllIn", new Singers(4, "Stray Kids")),
                //new Songs(6, "Miroh", new Singers(4, "Stray Kids")), new Songs(7, "Unpopular", new Singers(5, "Skilet")),
                new Songs(8, "Home", new Singers(6, "Three Days Grace")),
                new Songs(10, "Believer", new Singers(7, "Imagine Dragons")),
                new Songs(11, "Thunderous", new Singers(4, "Stray Kids")),
               new Songs (12,"Rapunzel",new Singers (2,"Water mill")),
                new Songs(13, "American Idiot", new Singers(10, "Greenish day")),
                new Songs(15, "Dugon", new Singers(1, "KingAndJocker")),
                        new Songs(17, "Wind Of Change", new Singers(13, "Scorpions"))

              //  new Songs(16, "The Upside", new Singers(11, "Lindsy Stirlink"))
        );

        when(songsRepository.findAll()).thenReturn(songs);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        songsServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(songs), sw.toString());
    }

}
