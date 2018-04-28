package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.Question;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueQuestionException;
import com.khamitcevich.model.jdbc.AbstractDao;
import com.khamitcevich.model.jdbc.ManipulationQuestion;
import com.khamitcevich.model.jdbc.QuestionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoJdbc extends AbstractDao<Question> implements QuestionDao {

    private static final String SELECT_ALL_SQL = "SELECT * FROM testingmedicaleployees.question";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM testingmedicaleployees.question WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM testingmedicaleployees.question WHERE id = ?";
    private static final String INSERT_SQL = "INSERT into testingmedicaleployees.question (idCategory, body) values (?, ?)";
    private static final String SELECT_BY_NAME_QUESTION = "SELECT id FROM testingmedicaleployees.question WHERE body = ?";

    public QuestionDaoJdbc() throws SQLException {
    }

    @Override
    public List<Question> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationQuestion());
    }

    @Override
    public Question selectById(int id) throws DBSystemException, SQLException {
        return selectById(SELECT_BY_ID_SQL, new ManipulationQuestion(), id);
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(Question question) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();

        if(uniqueData(conn, question))
            return insertOne(conn, question, new ManipulationQuestion(), INSERT_SQL);
        else
            return 0;
    }

    @Override
    public List<Integer> insertList(List<Question> questions) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        List<Integer> result = new ArrayList<Integer>();

        for (Question question : questions) {
            if (uniqueData(conn, question))
                result.add(insertOne(conn, question, new ManipulationQuestion(), INSERT_SQL));
        }
        return result;
    }

    @Override
    public boolean update(Question question) throws DBSystemException, SQLException {
        return false;
    }

    private boolean existWithQuestion0(Connection conn, String body) throws SQLException{
        PreparedStatement st = conn.prepareStatement(SELECT_BY_NAME_QUESTION);
        st.setString(1, body);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean uniqueData(Connection conn, Question question) throws SQLException {
        try {
            if (existWithQuestion0(conn, question.getBody())) {
                throw new NotUniqueQuestionException("QuestionDao '" + question.getBody() + "'");
            }
            return true;
        } catch (NotUniqueQuestionException e) {
            e.printStackTrace();
            return  false;
        }
    }
}
