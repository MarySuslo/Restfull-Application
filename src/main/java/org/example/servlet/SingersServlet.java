package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Singers;
import org.example.repository.SingersRepositoryImpl;
import org.example.service.impl.SingersServiceImpl;
import org.example.servlet.dto.SingersDto;
import org.example.servlet.mapper.SingersDtomapperImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SingersServlet", value = "/singer")
public class SingersServlet extends HttpServlet {

    private final SingersServiceImpl service;
    private final SingersDtomapperImpl dtomapper;
    private final SingersRepositoryImpl singersRepository;

    public SingersServlet() {

        this.dtomapper = new SingersDtomapperImpl();
        this.singersRepository = new SingersRepositoryImpl();
        this.service = new SingersServiceImpl(singersRepository);
    }

    private static Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String parametrId = req.getParameter("id");
        resp.setContentType("application/json");
        if (parametrId == null) {
            List<Singers> singers = singersRepository.findAll();
            resp.getWriter().write(gson.toJson(singers));
        } else {
            int id = Integer.parseInt(parametrId);
            Singers singerById = service.findById(id);

            SingersDto singerDto = dtomapper.mapToDto(singerById);
            resp.getWriter().write(gson.toJson(singerDto));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String parametrId = req.getParameter("idSinger");
        int id = Integer.parseInt(parametrId);
        String parametrName = req.getParameter("name");

        Singers singer = dtomapper.mapFromDto(new SingersDto(id, parametrName));

        service.save(singer);

        SingersDto singersDto = dtomapper.mapToDto(service.findById(singer.getIdSinger()));

        resp.getWriter().write(gson.toJson(singersDto));
    }
}
