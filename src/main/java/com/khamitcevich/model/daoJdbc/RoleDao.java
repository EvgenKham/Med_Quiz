package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Role;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueRoleTypeException;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    public List<Role> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert(Role role) throws DBSystemException, SQLException, NotUniqueRoleTypeException;

    public int [] insert(List<Role> roles) throws DBSystemException, SQLException, NotUniqueRoleTypeException;
}
