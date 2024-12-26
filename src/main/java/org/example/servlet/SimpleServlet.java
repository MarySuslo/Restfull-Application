package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Singers;
import org.example.model.Songs;
import org.example.repository.SingersRepositoryImpl;
import org.example.repository.SongsRepositoryImpl;
import org.example.service.SimpleService;
import org.example.service.impl.SimpleServiceImpl;
import org.example.servlet.dto.SongsDto;
import org.example.servlet.mapper.SimpleDtomapper;
import org.example.servlet.mapper.SimpleDtomapperImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "SimpleServlet", value = "")
public class SimpleServlet extends HttpServlet {

    private SimpleService service = new SimpleServiceImpl();
    private SimpleDtomapper dtomapper = new SimpleDtomapperImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String parametrId = req.getParameter("id");
        Gson gson = null;
        if (parametrId == null) {
            gson = new Gson();
            List<Songs> songs = new SongsRepositoryImpl().findAll();

            resp.setContentType("application/json");

            resp.getWriter().write(gson.toJson(songs));
        } else {
            gson = new Gson();

            int id = Integer.valueOf(parametrId);
            Songs byId = service.findById(id);

            SongsDto songsDto = dtomapper.map(byId);

            resp.setContentType("application/json");

            resp.getWriter().write(gson.toJson(songsDto));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String parametrId = req.getParameter("idSong");
        int id = Integer.valueOf(parametrId);
        String parametrName = req.getParameter("name");
        String parametrIdSinger = req.getParameter("idSinger");
        int idSinger = Integer.valueOf(parametrIdSinger);
        Singers singer = new SingersRepositoryImpl().findById(idSinger);

        Songs song = dtomapper.map(new SongsDto(id, parametrName, singer));

        service.saveSong(song);

        SongsDto songsDto = dtomapper.map(service.findById(song.getIdSong()));

        resp.setContentType("application/json");

        resp.getWriter().write(gson.toJson(songsDto));
    }

}
