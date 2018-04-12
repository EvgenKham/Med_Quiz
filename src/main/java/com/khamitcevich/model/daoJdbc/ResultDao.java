package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.exception.DBSystemException;
import com.khamitcevich.model.Result;

import java.sql.SQLException;
import java.util.List;

public interface ResultDao {
    public List<Result> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException;

    public int insert (Result result) throws DBSystemException;

    public int [] insert(List<Result> results) throws DBSystemException;
}
