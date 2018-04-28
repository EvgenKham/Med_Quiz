package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.User;

import java.sql.*;

public class ManipulationUser extends Manipulation<User> {
    @Override
    public User extractOne(ResultSet rs) throws SQLException {
        User user = new User(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setTimesheetNumber(rs.getInt("timesheetNumber"));
        user.setIdRole(rs.getInt("idRole"));
        //role.setIdUser(null); если бы было такое поле, которое связано с отношением User
        return user;
    }

    public int addOne(PreparedStatement ps, User user) throws SQLException {

        ResultSet rs;
        int result = 0;
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setInt(4, user.getTimesheetNumber());
        ps.setInt(5, user.getIdRole());
        ps.executeUpdate();
        //Получение первичного ключа сгенерированного базой
        rs = ps.getGeneratedKeys();
        while (rs.next())
            result = rs.getInt(1);

        JdbcUtils.closeQuietly(rs);
        return result;
    }

}
