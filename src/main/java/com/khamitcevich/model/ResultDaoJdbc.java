package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.Result;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.jdbc.ManipulationResult;
import com.khamitcevich.model.jdbc.ResultDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDaoJdbc extends AbstractDao<Result> implements ResultDao {

    private static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.result";
    private static final String SELECT_BY_ID_SQL = "Select * From testingmedicaleployees.result Where id = ?";
    private static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.result Where id = ?";
    private static final String INSERT_ONE_SQL = "Insert into testingmedicaleployees.result (typeTest, percent, date, idUser) values (?, ?, ?, ?)";
//    public static final String SELECT_BY_TYPE_TEST = "SELECT id FROM testingmedicaleployees.result WHERE typeTest = ?";
//    public static final String SELECT_BY_PERCENT = "SELECT id FROM testingmedicaleployees.result WHERE percent = ?";
//    public static final String SELECT_BY_DATA = "SELECT id FROM testingmedicaleployees.result WHERE date = ?";
//    public static final String SELECT_BY_ID_USER = "SELECT id FROM testingmedicaleployees.result WHERE idUser = ?";

    public ResultDaoJdbc() throws SQLException {
    }

    @Override
    public List<Result> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationResult());
    }

    @Override
    public Result selectById(int id) throws DBSystemException, SQLException {
        return selectById(SELECT_BY_ID_SQL, new ManipulationResult(), id);
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(Result result) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        return insertOne(conn, result, new ManipulationResult(), INSERT_ONE_SQL);
    }

    @Override
    public List<Integer> insertList(List<Result> results) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();

        List<Integer> resultId = new ArrayList<Integer>();
        for(Result result: results) {
            resultId.add(insertOne(conn, result, new ManipulationResult(), INSERT_ONE_SQL));
        }
        return resultId;
    }

    @Override
    public boolean update(Result result) throws DBSystemException, SQLException {
        return false;
    }
}
