package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.entitiesDB.Result;

import java.sql.SQLException;
import java.util.List;

public interface ResultDao {
    public List<Result> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert (Result result) throws DBSystemException, SQLException;

    public int [] insert(List<Result> results) throws DBSystemException, SQLException;
}
