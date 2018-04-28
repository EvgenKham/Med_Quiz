package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManipulationResult extends Manipulation<Result> {

    @Override
    public Result extractOne(ResultSet rs) throws SQLException {
        Result result = new Result(rs.getInt("id"));
        result.setTypeTest(rs.getString("typeTest") );
        result.setPercent(rs.getFloat("percent"));
        result.setDate(rs.getDate("date"));
        result.setIdUser(rs.getInt("idUser"));
        return result;
    }

    @Override
    public int addOne(PreparedStatement ps, Result result) throws SQLException {
        ResultSet resultSet;
        int res = 0;
        ps.setString(1, result.getTypeTest());
        ps.setFloat(2, result.getPercent());
        ps.setDate(3, (java.sql.Date) result.getDate());
        ps.setInt(4, result.getIdUser());
        ps.executeUpdate();
        //Получение первичного ключа сгенерированного базой
        resultSet = ps.getGeneratedKeys();
        while (resultSet.next())
            res = resultSet.getInt(1);

        JdbcUtils.closeQuietly(resultSet);
        return res;
    }
}
