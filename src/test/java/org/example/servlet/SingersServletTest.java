package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.example.service.impl.SingersServiceImpl;
import org.example.servlet.dto.SingersDto;
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
        String nameSinger = "KingAndJocker";
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

        when(request.getParameter("idSinger")).thenReturn("6");
        when(request.getParameter("name")).thenReturn("Ateez");

        int id = 6;
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

        when(request.getParameter("idSinger")).thenReturn("3");
        when(request.getParameter("name")).thenReturn("Cinema");

        int id = 3;
        String nameSinger = "Cinema";
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

        when(request.getParameter("id")).thenReturn("6");

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

        Singers singer1 = new Singers(1, "KingAndJocker");
        Singers singer2 = new Singers(2, "Windmill");
        Singers singer3 = new Singers(3, "Cinema");

        List<Singers> singers = Arrays.asList(singer1, singer2, singer3);

        when(singersRepository.findAll()).thenReturn(singers);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        singersServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        assertEquals(gson.toJson(singers), sw.toString());
    }

}
