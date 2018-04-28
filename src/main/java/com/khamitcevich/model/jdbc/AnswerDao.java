package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Answer;
import com.khamitcevich.model.exception.DBSystemException;

import java.sql.SQLException;
import java.util.List;

public interface AnswerDao extends Dao<Answer> {

    List<Answer> selectAll() throws DBSystemException, SQLException;

    Answer selectById(int id) throws DBSystemException, SQLException;

    int insertOne(Answer answer) throws DBSystemException, SQLException;

    List<Integer> insertList(List<Answer> answers) throws DBSystemException, SQLException;

    boolean update(Answer answer) throws DBSystemException, SQLException;
}
