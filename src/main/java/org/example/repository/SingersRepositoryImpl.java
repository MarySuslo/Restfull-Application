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

    String insertSQL = "INSERT INTO singers (id_singer, name_singer) VALUES (?, ?) ";
    String findIdSQL = "SELECT * FROM singers WHERE id_singer=?";
    String deleteByIdSQL = "DELETE from singers WHERE id_singer=?";
    String findAllSQL = "SELECT * FROM singers ORDER BY id_singer;";
    String updateSingerSQL = "UPDATE singers SET name_singer=? WHERE id_singer=?";

    @Override
    public Singers findById(int id) {
        connectionManager = new ConnectionManagerImpl();
        Singers singer = null;
        try (Connection connection = connectionManager.getConnection()) {

            try (PreparedStatement prStatement = connection.prepareStatement(findIdSQL)) {
                prStatement.setInt(1, id);
                ResultSet result = prStatement.executeQuery();
                if (result.next()) {
                    singer = map(result);
                }
            }
            if (singer == null)
                throw new SQLException();

        } catch (SQLException e) {
            throw new NotFoundException("Исполнителя с таким индексон не найдено");
        }
        return singer;
    }

    @Override
    public boolean deleteById(int id) {

        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {

            try (PreparedStatement psDeleteSongsOfSingers = connection.prepareStatement("delete from songs where singer=" + id + ";")) {
                psDeleteSongsOfSingers.executeUpdate();
            }

            try (PreparedStatement psDeleteSinger = connection.prepareStatement(deleteByIdSQL)) {
                psDeleteSinger.setInt(1, id);
                return psDeleteSinger.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка удаления");
        }
    }

    @Override
    public List<Singers> findAll() {
        connectionManager = new ConnectionManagerImpl();
        List<Singers> singersList = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {

            try (PreparedStatement prStatement = connection.prepareStatement(findAllSQL)) {
                ResultSet result = prStatement.executeQuery();

                while (result.next()) {
                    singersList.add(map(result));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка чтения");
        }
        return singersList;
    }

    @Override
    public boolean save(Singers singer) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(insertSQL)) {
                prStatement.setInt(1, singer.getIdSinger());
                prStatement.setString(2, singer.getNameSinger());

                prStatement.executeUpdate();
            }
            return singer.getIdSinger() > 0;

        } catch (SQLException e) {
            throw new DuplicateDataException("Исполнитель с таким индексом уже существует");
        }
    }

    @Override
    public boolean update(Singers singer) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(updateSingerSQL)) {
                prStatement.setString(1, singer.getNameSinger());
                prStatement.setInt(2, singer.getIdSinger());

                if (prStatement.executeUpdate() > 0)
                    return true;
                else throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка обновления");
        }

    }

    @Override
    public Singers map(ResultSet result) throws SQLException {

        int id = result.getInt("id_singer");
        String nameSinger = result.getString("name_singer");
        return new Singers(id, nameSinger);
    }
}
