package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Question;
import com.khamitcevich.model.exception.DBSystemException;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDao extends Dao<Question> {

    List<Question> selectAll () throws DBSystemException, SQLException;

    Question selectById (int id) throws DBSystemException, SQLException;

    int insertOne(Question question) throws DBSystemException, SQLException;

    List<Integer> insertList(List<Question> questions) throws DBSystemException, SQLException;

    boolean update(Question question) throws DBSystemException, SQLException;
}

