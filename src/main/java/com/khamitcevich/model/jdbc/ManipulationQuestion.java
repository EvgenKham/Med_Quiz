package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManipulationQuestion extends Manipulation<Question> {

    @Override
    public Question extractOne(ResultSet rs) throws SQLException {
        Question question = new Question(rs.getInt("id"));
        question.setIdCategory(rs.getInt("idCategory"));
        question.setBody(rs.getString("body"));
        return question;
    }

    @Override
    public int addOne(PreparedStatement ps, Question question) throws SQLException {
        ResultSet resultSet;
        int result = 0;

        ps.setInt(1, question.getIdCategory());
        ps.setString(2, question.getBody());
        ps.executeUpdate();
        resultSet = ps.getGeneratedKeys();
        while (resultSet.next())
            result = resultSet.getInt(1);

        JdbcUtils.closeQuietly(resultSet);
        return result;
    }
}
