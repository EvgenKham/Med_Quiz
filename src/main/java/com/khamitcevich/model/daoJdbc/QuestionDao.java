package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Question;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueQuestionException;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDao {
    public List<Question> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert(Question question) throws DBSystemException, SQLException, NotUniqueQuestionException;

    public int [] insert(List<Question> questions) throws DBSystemException, SQLException, NotUniqueQuestionException;
}
