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
import org.example.service.impl.SongsServiceImpl;
import org.example.servlet.dto.SingersDto;
import org.example.servlet.dto.SongsDto;
import org.example.servlet.mapper.SongsDtomapperImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "SongsServlet", value = "")
public class SongsServlet extends HttpServlet {

    private final SongsServiceImpl service;
    private final SongsDtomapperImpl dtomapper;
    private final SingersRepositoryImpl singersRepository;
    private final SongsRepositoryImpl songsRepository;


    public SongsServlet() {

        this.dtomapper = new SongsDtomapperImpl();
        this.singersRepository = new SingersRepositoryImpl();
        this.songsRepository = new SongsRepositoryImpl();
        this.service = new SongsServiceImpl(songsRepository);
    }

    private static Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String parametrId = req.getParameter("id");
        resp.setContentType("application/json");
        if (parametrId == null) {
            List<Songs> songs = songsRepository.findAll();
            resp.getWriter().write(gson.toJson(songs));
        } else {
            int id = Integer.parseInt(parametrId);
            Songs songById = service.findById(id);

            SongsDto songsDto = dtomapper.mapToDto(songById);
            resp.getWriter().write(gson.toJson(songsDto));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String parametrId = req.getParameter("idSong");
        int id = Integer.parseInt(parametrId);
        String parametrName = req.getParameter("name");
        String parametrIdSinger = req.getParameter("idSinger");
        int idSinger = Integer.parseInt(parametrIdSinger);

        Singers singer = singersRepository.findById(idSinger);

        Songs song = dtomapper.mapFromDto(new SongsDto(id, parametrName, singer));

        service.save(song);

        SongsDto songsDto = dtomapper.mapToDto(service.findById(song.getIdSong()));

        resp.getWriter().write(gson.toJson(songsDto));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String parametrId = req.getParameter("idSong");
        int id = Integer.parseInt(parametrId);
        String parametrName = req.getParameter("name");
        String parametrIdSinger = req.getParameter("idSinger");
        int idSinger = Integer.parseInt(parametrIdSinger);

        Singers singer = singersRepository.findById(idSinger);

        Songs song = dtomapper.mapFromDto(new SongsDto(id, parametrName, singer));

        service.update(song);

        SongsDto songsDto = dtomapper.mapToDto(service.findById(song.getIdSong()));

        resp.getWriter().write(gson.toJson(songsDto));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String parametrId = req.getParameter("id");
        resp.setContentType("application/json");

        int id = Integer.parseInt(parametrId);

        service.delete(id);

        resp.getWriter().write(gson.toJson("{\"message\": \"Song deleted successfully\"}"));

    }
}
