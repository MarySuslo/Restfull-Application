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
import java.util.UUID;

public class SongsRepositoryImpl implements SimpleRepository<Songs> {

    private ConnectionManager connectionManager;

    @Override
    public Songs findById(int song_id) {
        connectionManager = new ConnectionManagerImpl();

        Songs song = null;
        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs " +
                    "where id_song=" + song_id + " order by id_song");
            ResultSet result = prStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id_song");
                String nameSong = result.getString("name_song");

                Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));
                song = new Songs(id, nameSong, singer);
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        if (song == null)
            throw new NotFoundException("Песни с таким индексон не найдено");

        return song;
    }

    @Override
    public boolean deleteById(int id) {

        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement("delete from songs where id_song=" + id + ";");

            int result = prStatement.executeUpdate();

            if (result == 0)
                throw new SQLException("Ошибка удаления");

            return true;
        } catch (SQLException e) {
            throw new DataBaseException("Ошибка удаления");
        }
    }

    @Override
    public List<Songs> findAll() {
        connectionManager = new ConnectionManagerImpl();

        List<Songs> songsList = new ArrayList<Songs>();

        try (Connection connection = connectionManager.getConnection()) {

            PreparedStatement prStatement = connection.prepareStatement("select * from songs order by id_song");
            ResultSet result = prStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id_song");
                String nameSong = result.getString("name_song");

                Singers singer = new SingersRepositoryImpl().findById(result.getInt("singer"));

                songsList.add(new Songs(id, nameSong, singer));
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        return songsList;
    }

    @Override
    public boolean save(Songs song) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement("insert into songs values (" +
                    song.getIdSong() + ", '" + song.getNameSong() + "'," + song.getSinger().getIdSinger() + ") ;");
            prStatement.executeUpdate();
            song.getSinger().getSongs().add(song);
            return true;
        } catch (SQLException e) {
            throw new DuplicateDataException("Песня с таким индексом уже существует");
        }
    }

    @Override
    public List<Songs> update(Songs song) {
        connectionManager = new ConnectionManagerImpl();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement("update songs set " +
                    " name_song= '" + song.getNameSong() + "'" +
                    ", singer=" + song.getSinger().getIdSinger() +
                    " where id_song=" + song.getIdSong() + ";");

            int result = prStatement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Ошибка обновления");

            }

        } catch (SQLException e) {
            e.getMessage();
            throw new DataBaseException("Ошибка обновления");
        }
        return findAll();
    }
}
