package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManipulationCategoty extends Manipulation<Category> {
    @Override
    public Category extractOne(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getInt("id"));
        category.setName(rs.getString("nameCategory"));
        return category;
    }

    @Override
    public int addOne(PreparedStatement ps, Category category) throws SQLException {
        ResultSet resultSet;
        int res = 0;

        ps.setString(1, category.getName());
        ps.executeUpdate();
        //Получение первичного ключа сгенерированного базой
        resultSet = ps.getGeneratedKeys();
        while (resultSet.next())
            res = resultSet.getInt(1);

        JdbcUtils.closeQuietly(resultSet);
        return res;
    }
}
