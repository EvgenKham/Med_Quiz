package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.jdbc.AbstractDao;
import com.khamitcevich.model.jdbc.ManipulationUser;
import com.khamitcevich.model.jdbc.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc extends AbstractDao<User> implements UserDao {

    private static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.user";
    private static final String SELECT_BY_ID_SQL = "Select * From testingmedicaleployees.user Where id = ?";
    private static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.user Where id = ?";
    private static final String INSERT_SQL = "Insert into testingmedicaleployees.user (login, password, email, timesheetNumber, idRole) values (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT id FROM testingmedicaleployees.user WHERE email = ?";
    private static final String SELECT_BY_LOGIN_SQL = "SELECT id FROM testingmedicaleployees.user WHERE login = ?";
    private static final String SELECT_BY_PASSWORD_SQL = "SELECT id FROM testingmedicaleployees.user WHERE password = ?";
    private static final String SELECT_BY_TIMESHEET_NUMBER_SQL = "SELECT id FROM testingmedicaleployees.user WHERE timesheetNumber = ?";

    public UserDaoJdbc() throws SQLException {
    }

    @Override
    public List<User> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationUser());
    }

    @Override
    public User selectById(int id) throws DBSystemException, SQLException {
        return selectById(SELECT_BY_ID_SQL, new ManipulationUser(), id);
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(User user) throws DBSystemException, SQLException  {
        Connection conn = getSerializableConnection();

        if(uniqueData(conn, user))
            return insertOne(conn, user, new ManipulationUser(), INSERT_SQL);
        else
            return 0;
    }

    @Override
    public List<Integer> insertList(List<User> users) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        List<Integer> result = new ArrayList<Integer>();

        for (User user : users) {
            if(uniqueData(conn, user))
                result.add(insertOne(conn, user, new ManipulationUser(), INSERT_SQL));
        }
        return result;
    }

    @Override
    public boolean update(User user) throws DBSystemException, SQLException {
        return false;
    }

    private boolean existWithLogin0(Connection conn, String login) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_LOGIN_SQL);
        st.setString(1, login);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithPassword0(Connection conn, String password) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_PASSWORD_SQL);
        st.setString(1, password);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithEmail0(Connection conn, String email) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_EMAIL_SQL);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithTimesheetNumber0(Connection conn, int timesheetNumber) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_TIMESHEET_NUMBER_SQL);
        st.setInt(1, timesheetNumber);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean uniqueData(Connection conn, User user) throws DBSystemException, SQLException {
        try {
            if (existWithLogin0(conn, user.getLogin())) {
                throw new NotUniqueUserLoginException("Login '" + user.getLogin() + "'");
            }
            if (existWithPassword0(conn, user.getPassword())) {
                throw new NotUniqueUserPasswordException("Password '" + user.getPassword() + "'");
            }
            if (existWithEmail0(conn, user.getEmail())) {
                throw new NotUniqueUserEmailException("Email '" + user.getEmail() + "'");
            }
            if (existWithTimesheetNumber0(conn, user.getTimesheetNumber())) {
                throw new NotUniqueUserTimesheetNumberException("TimesheetNumber '" + user.getTimesheetNumber() + "'");
            }
            return true;
        } catch (NotUniqueUserEmailException e) {
            e.printStackTrace();
            return  false;
        } catch (NotUniqueUserPasswordException e) {
            e.printStackTrace();
            return  false;
        } catch (NotUniqueUserLoginException e) {
            e.printStackTrace();
            return  false;
        } catch (NotUniqueUserTimesheetNumberException e) {
            e.printStackTrace();
            return  false;
        }
    }
}
