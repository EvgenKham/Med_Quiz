package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Role;
import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao extends Dao<Role> {
    List<Role> selectAll () throws DBSystemException, SQLException;

    Role selectById (int id) throws DBSystemException, SQLException;

    int insertOne(Role role) throws DBSystemException, SQLException;

    List<Integer> insertList(List<Role> role) throws DBSystemException, SQLException;

    boolean update(Role role) throws DBSystemException, SQLException;
}
