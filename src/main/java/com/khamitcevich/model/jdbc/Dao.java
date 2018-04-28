package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    List<T> selectAll() throws DBSystemException, SQLException;

    T selectById (int id) throws DBSystemException, SQLException;

    int deleteById (int id ) throws DBSystemException, SQLException;

    int insertOne(T newEntity) throws DBSystemException, SQLException;

    List<Integer> insertList(List<T> newEntity) throws DBSystemException, SQLException;

    boolean update(T newEntity) throws DBSystemException, SQLException;

}
