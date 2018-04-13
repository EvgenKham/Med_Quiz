package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    public List<User> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert (User user) throws DBSystemException, SQLException, NotUniqueUserPasswordException, NotUniqueUserLoginException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException;

    public int [] insert(List<User> users) throws DBSystemException, SQLException, NotUniqueUserLoginException, NotUniqueUserPasswordException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException;
}
