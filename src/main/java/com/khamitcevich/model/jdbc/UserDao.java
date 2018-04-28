package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends Dao<User> {

    List<User> selectAll () throws DBSystemException, SQLException;

    User selectById (int id) throws DBSystemException, SQLException;

    int insertOne(User user) throws DBSystemException, SQLException;

    List<Integer> insertList(List<User> user) throws DBSystemException, SQLException;

    boolean update(User user) throws DBSystemException, SQLException;
}
