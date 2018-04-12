package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.exception.*;
import com.khamitcevich.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    public List<User> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert (User user) throws DBSystemException, SQLException, NotUniqueUserLoginException, NotUniqueUserPasswordException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException;

    public int [] insert(List<User> users) throws DBSystemException, SQLException, NotUniqueUserLoginException, NotUniqueUserPasswordException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException;
}
