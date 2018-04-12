package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.exception.DBSystemException;
import com.khamitcevich.model.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    public List<Role> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException;

    public int insert (Role role) throws DBSystemException;

    public int [] insert(List<Role> roles) throws DBSystemException;
}
