package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.Answer;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.jdbc.AbstractDao;
import com.khamitcevich.model.jdbc.AnswerDao;
import com.khamitcevich.model.jdbc.ManipulationAnswer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoJdbc extends AbstractDao<Answer> implements AnswerDao {

    private static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.answer";
    private static final String SELECT_BY_ID_SQL = "Select * From testingmedicaleployees.answer Where id = ?";
    private static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.answer Where id = ?";
    private static final String INSERT_SQL = "Insert into testingmedicaleployees.answer (idQuestion, version, isRight) values (?, ?, ?)";

    public AnswerDaoJdbc() throws SQLException {
    }

    @Override
    public List<Answer> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationAnswer());
    }

    @Override
    public Answer selectById(int id) throws DBSystemException, SQLException {
        return selectById(SELECT_BY_ID_SQL, new ManipulationAnswer(), id);
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(Answer answer) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        return insertOne(conn, answer, new ManipulationAnswer(), INSERT_SQL);
    }

    @Override
    public List<Integer> insertList(List<Answer> answers) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        List<Integer> result = new ArrayList<Integer>();

        for (Answer answer : answers) {
            result.add(insertOne(conn, answer, new ManipulationAnswer(), INSERT_SQL));
        }
        return result;
    }

    @Override
    public boolean update(Answer answer) throws DBSystemException, SQLException {
        return false;
    }
}
