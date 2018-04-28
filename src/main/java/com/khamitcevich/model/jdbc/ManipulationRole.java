package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Role;

import java.sql.*;

public class ManipulationRole extends Manipulation<Role> {

    public Role extractOne (ResultSet rs) throws SQLException {
        Role role = new Role(rs.getInt("id"));
        role.setType(rs.getString("type"));
        return role;
    }

    @Override
    public int addOne(PreparedStatement ps, Role role) throws SQLException {
        ResultSet rs;
        int result = 0;
        ps.setString(1, role.getType());
        ps.executeUpdate();
        //Получение первичного ключа сгенерированного базой
        rs = ps.getGeneratedKeys();
        while (rs.next())
            result = rs.getInt(1);
        JdbcUtils.closeQuietly(rs);
        return result;
    }

}
