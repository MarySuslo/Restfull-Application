package org.example.repository;

import org.example.Errors.DataBaseException;
import org.example.Errors.DuplicateDataException;
import org.example.Errors.NotFoundException;
import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.Singers;
import org.example.model.Songs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongsRepositoryImpl implements SimpleRepository<Songs> {

    private ConnectionManager connectionManager;

    String insertSQL = "INSERT INTO songs (id_song, name_song, singer) VALUES (?, ?, ?) ";
    String findIdSQL = "SELECT * FROM songs WHERE id_song=?";
    String deleteByIdSQL = "DELETE FROM songs WHERE id_song=?";
    String findAllSQL = "SELECT * FROM songs ORDER BY id_song;";
    String updateSongSQL = "UPDATE songs SET name_song=?, singer=? WHERE id_song=?";

    @Override
    public Songs findById(int songId) {
        connectionManager = new ConnectionManagerImpl();

        Songs song = null;
        try (Connection connection = connectionManager.getConnection()) {

            try (PreparedStatement prStatement = connection.prepareStatement(findIdSQL)) {
                prStatement.setInt(1, songId);
                ResultSet result = prStatement.executeQuery();
                if (result.next()) {
                    song = map(result);
                }
            }
            if (song != null) {
                return song;
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new NotFoundException("Песни с таким индексон не найдено");
        }

    }

    @Override
    public boolean deleteById(int id) {

        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(deleteByIdSQL)) {
                prStatement.setInt(1, id);
                return prStatement.executeUpdate() > 0;

            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка удаления");
        }
    }

    @Override
    public List<Songs> findAll() {
        connectionManager = new ConnectionManagerImpl();

        List<Songs> songsList = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection()) {

            try (PreparedStatement prStatement = connection.prepareStatement(findAllSQL)) {
                ResultSet result = prStatement.executeQuery();

                while (result.next()) {
                    songsList.add(map(result));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка чтения");
        }

        return songsList;
    }

    @Override
    public boolean save(Songs song) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(insertSQL)) {
                prStatement.setInt(1, song.getIdSong());
                prStatement.setString(2, song.getNameSong());
                prStatement.setInt(3, song.getSinger().getIdSinger());
                prStatement.executeUpdate();
                song.getSinger().getSongs().add(song);

                return song.getIdSong() > 0;
            }
        } catch (SQLException e) {
            throw new DuplicateDataException("Песня с таким индексом уже существует");
        }
    }

    @Override
    public boolean update(Songs song) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement prStatement = connection.prepareStatement(updateSongSQL)) {
                prStatement.setString(1, song.getNameSong());
                prStatement.setInt(2, song.getSinger().getIdSinger());
                prStatement.setInt(3, song.getIdSong());

                return prStatement.executeUpdate() > 0;

            }
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка обновления");
        }
    }

    @Override
    public Songs map(ResultSet result) throws SQLException {

        int id = result.getInt("id_song");
        String nameSong = result.getString("name_song");
        Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));

        return new Songs(id, nameSong, singer);
    }
}
