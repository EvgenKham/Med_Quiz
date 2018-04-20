package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Answer;
import com.khamitcevich.model.exception.DBSystemException;

import java.sql.SQLException;
import java.util.List;

public interface AnswerDao {
    public List<Answer> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert(Answer answer) throws DBSystemException, SQLException;

    public int [] insert(List<Answer> answers) throws DBSystemException, SQLException;
}
