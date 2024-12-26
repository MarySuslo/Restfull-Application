package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Songs;
import org.example.repository.SongsRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;

class SimpleServletTest {
    //мокаем сервис и другие зависимости
    // проевряем логику роута на нужные метода

    @Test
    void doGet() throws ServletException, IOException {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);
        servlet.doGet(request, response);
        String result = sw.toString();

        Songs song = new SongsRepositoryImpl().findById(1);
        Gson gson = new Gson();
        assertEquals(gson.toJson(song), result);
    }

    @Test
    void doPost() throws ServletException, IOException {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("idSong")).thenReturn("6");
        when(request.getParameter("name")).thenReturn("Dugon");
        when(request.getParameter("idSinger")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);
        servlet.doPost(request, response);
        String result = sw.toString();
        Songs song = new SongsRepositoryImpl().findById(6);
        Gson gson = new Gson();
        assertEquals(gson.toJson(song), result);

    }

    @Test
    void doGetStartr() throws ServletException, IOException {
        SimpleServlet servlet = new SimpleServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);
        servlet.doGet(request, response);
        String result = sw.toString();

        List<Songs> songs = new SongsRepositoryImpl().findAll();
        Gson gson = new Gson();
        assertEquals(gson.toJson(songs), result);
    }
}
