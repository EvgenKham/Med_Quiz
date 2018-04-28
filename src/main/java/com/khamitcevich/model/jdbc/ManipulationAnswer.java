package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Answer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManipulationAnswer extends Manipulation<Answer> {
    @Override
    public Answer extractOne(ResultSet rs) throws SQLException {
        Answer answer = new Answer(rs.getInt("id"));
        answer.setIdQuestion(rs.getInt("idQuestion"));
        answer.setVersion(rs.getString("version"));
        answer.setIsRight(rs.getString("isRight"));
        return answer;
    }

    @Override
    public int addOne(PreparedStatement ps, Answer answer) throws SQLException {
        ResultSet resultSet;
        int result = 0;

        ps.setInt(1, answer.getIdQuestion());
        ps.setString(2, answer.getVersion());
        ps.setString(3, answer.getIsRight());
        ps.executeUpdate();
        //Получение первичного ключа сгенерированного базой
        resultSet = ps.getGeneratedKeys();
        while (resultSet.next())
            result = resultSet.getInt(1);

        JdbcUtils.closeQuietly(resultSet);
        return result;
    }
}
