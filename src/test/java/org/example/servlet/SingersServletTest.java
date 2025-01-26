package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.DataBaseContext;
import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.example.service.impl.SingersServiceImpl;
import org.example.servlet.dto.SingersDto;
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
public class SingersServletTest {

    @Mock
    private SingersServiceImpl singersService;

    @Mock
    private SimpleDtomapper<Singers, SingersDto> singersMapper;

    @Mock
    private SingersRepositoryImpl singersRepository;

    @InjectMocks
    private SingersServlet singersServlet;

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

        when(request.getParameter("id")).thenReturn("4");

        int id = 4;
        String nameSinger = "Stray Kids";
        Singers singer = new Singers(id, nameSinger);
        SingersDto singerDto = new SingersDto(id, nameSinger);

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
    void doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSinger")).thenReturn("12");
        when(request.getParameter("name")).thenReturn("Ateez");

        int id = 12;
        String nameSinger = "Ateez";
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
    void doPut() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSinger")).thenReturn("10");
        when(request.getParameter("name")).thenReturn("Greenish day");

        int id = 10;
        String nameSinger = "Greenish day";
        Singers singer = new Singers(id, nameSinger);
        SingersDto singerDto = new SingersDto(id, nameSinger);

        when(singersMapper.mapFromDto(any(SingersDto.class))).thenReturn(singer);
        when(singersService.findById(id)).thenReturn(singer);
        when(singersMapper.mapToDto(singer)).thenReturn(singerDto);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doPut(request, response);
        verify(response).setContentType("application/json");

        assertEquals(gson.toJson(singerDto), sw.toString());

    }

    @Test
    void doDelete() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("11");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doDelete(request, response);
        verify(response).setContentType("application/json");

        assertEquals("\"{\\\"message\\\": \\\"Singer deleted successfully\\\"}\"", sw.toString());

    }

    @Test
    void doGetStart() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        List<Singers> singers = Arrays.asList(new Singers(1, "KingAndJocker"),
                new Singers(2, "Windmill"),
                new Singers(3, "Movie"),
                new Singers(4, "Stray Kids"),
                new Singers(5, "Skilet"),
                new Singers(6, "Three Days Grace"),
                new Singers(7, "Imagine Dragons"),
                new Singers(10, "Greenish day"),
                new Singers(12, "Ateez"),
                        new Singers(13, "Scorpions")
        );

        when(singersRepository.findAll()).thenReturn(singers);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(singers), sw.toString());
    }

}
