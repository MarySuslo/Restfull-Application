package org.example.repository;

import org.example.Errors.DataBaseException;
import org.example.Errors.DuplicateDataException;
import org.example.Errors.NotFoundException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Singers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SingersRepositoryImpl implements SimpleRepository<Singers> {

    private ConnectionManager connectionManager;

    @Override
    public Singers findById(int singer_id) {
        connectionManager = new ConnectionManagerImpl();
        Singers singer = null;
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from singers " +
                    "where id_singer=" + singer_id + " order by id_singer");
            ResultSet result = prStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id_singer");
                String nameSinger = result.getString("name_singer");

                singer = new Singers(id, nameSinger);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        if (singer == null)
            throw new NotFoundException("Исполнителя с таким индексон не найдено");
        return singer;
    }

    @Override
    public boolean deleteById(int id) {

        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement psDeleteSongsOfSingers = connection.prepareStatement("delete from songs where singer=" + id + ";");
            psDeleteSongsOfSingers.executeUpdate();

            PreparedStatement psDeleteSinger = connection.prepareStatement("delete from singers where id_singer=" + id + ";");

            int result = psDeleteSinger.executeUpdate();

            if (result == 0) {
                throw new SQLException();
            }

            return true;
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка удаления");
        }
    }

    @Override
    public List<Singers> findAll() {
        connectionManager = new ConnectionManagerImpl();
        List<Singers> singersList = new ArrayList<Singers>();
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from singers order by id_singer;");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_singer");
                String nameSinger = result.getString("name_singer");
                Singers singer = new Singers(id, nameSinger);

                singersList.add(singer);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return singersList;
    }

    @Override
    public boolean save(Singers singer) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement("insert into singers values (" +
                    singer.getIdSinger() + ", '" + singer.getNameSinger() + "');");
            prStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DuplicateDataException("Исполнитель с таким индексом уже существует");
        }
    }

    @Override
    public List<Singers> update(Singers singer) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement("update singers set " +
                    " name_singer='" + singer.getNameSinger() + "' " +
                    " where id_singer=" + singer.getIdSinger() + ";");

            int result = prStatement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Ошибка обновления");
            }

        } catch (SQLException e) {
            throw new DataBaseException("Ошибка обновления");
        }
        return findAll();
    }
}
