package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.user";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.user Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.user (login, password, email, timesheetNumber, idRole) values (?, ?, ?, ?, ?)";
    public static final String SELECT_BY_EMAIL = "SELECT id FROM testingmedicaleployees.user WHERE email = ?";
    public static final String SELECT_BY_LOGIN = "SELECT id FROM testingmedicaleployees.user WHERE login = ?";
    public static final String SELECT_BY_PASSWORD = "SELECT id FROM testingmedicaleployees.user WHERE password = ?";
    public static final String SELECT_BY_TIMESHEET_NUMBER = "SELECT id FROM testingmedicaleployees.user WHERE timesheetNumber = ?";

    public UserDaoJdbc() throws SQLException {
    }


    @Override
    public List<User> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<User> result = new ArrayList<User>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String email = rs.getString("email");
                int timesheetNumber = rs.getInt("timesheetNumber");
                int idRole = rs.getInt("idRole");                   //???
                User user = new User(id);
                user.setLogin(login);
                user.setPassword(password);
                user.setEmail(email);
                user.setTimesheetNumber(timesheetNumber);
                user.setIdRole(idRole);
                result.add(user);
            }
            conn.commit();
            return result;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + SELECT_ALL_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(DELETE_BY_ID_SQL);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            conn.commit();
            return result;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + DELETE_BY_ID_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int insert(User user) throws DBSystemException, SQLException, NotUniqueUserLoginException, NotUniqueUserPasswordException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

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

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
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

            conn.commit();
            return result;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + INSERT_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
            JdbcUtils.closeQuietly(rs);
        }
    }

    @Override
    public int[] insert(List<User> users) throws DBSystemException,  SQLException, NotUniqueUserLoginException, NotUniqueUserPasswordException, NotUniqueUserEmailException, NotUniqueUserTimesheetNumberException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        int count [] = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for(User user: users) {
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

                ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getEmail());
                ps.setInt(4, user.getTimesheetNumber());
                ps.setInt(5, user.getIdRole());
                ps.addBatch();
                count = ps.executeBatch();
            }
            conn.commit();
            return count;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + INSERT_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    private boolean existWithLogin0(Connection conn, String login) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_LOGIN);
        st.setString(1, login);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithPassword0(Connection conn, String password) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_PASSWORD);
        st.setString(1, password);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithEmail0(Connection conn, String email) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_EMAIL);
        st.setString(1, email);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean existWithTimesheetNumber0(Connection conn, int timesheetNumber) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_TIMESHEET_NUMBER);
        st.setInt(1, timesheetNumber);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
