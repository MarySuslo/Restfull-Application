package org.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SimpleRepository<T> {

    T findById(int id);

    boolean deleteById(int id);

    List<T> findAll();

    boolean save(T t);

    boolean update(T t);

    T map(ResultSet resultSet) throws SQLException;
}

