package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Result;
import com.khamitcevich.model.exception.DBSystemException;

import java.sql.SQLException;
import java.util.List;

public interface ResultDao extends Dao<Result> {

    List<Result> selectAll () throws DBSystemException, SQLException;

    Result selectById (int id) throws DBSystemException, SQLException;

    int insertOne(Result result) throws DBSystemException, SQLException;

    List<Integer> insertList(List<Result> result) throws DBSystemException, SQLException;

    boolean update(Result result) throws DBSystemException, SQLException;
}
