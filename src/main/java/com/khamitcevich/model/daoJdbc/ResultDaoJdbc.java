package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Result;
import com.khamitcevich.model.entitiesDB.Role;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueRoleTypeException;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.Date ;
import java.util.ArrayList;
import java.util.List;

public class ResultDaoJdbc implements ResultDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.result";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.result Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.result (typeTest, percent, date, idUser) values (?, ?, ?, ?)";
    public static final String SELECT_BY_TYPE_TEST = "SELECT id FROM testingmedicaleployees.result WHERE typeTest = ?";
    public static final String SELECT_BY_PERCENT = "SELECT id FROM testingmedicaleployees.result WHERE percent = ?";
    public static final String SELECT_BY_DATA = "SELECT id FROM testingmedicaleployees.result WHERE date = ?";
    public static final String SELECT_BY_ID_USER = "SELECT id FROM testingmedicaleployees.result WHERE idUser = ?";

    public ResultDaoJdbc () throws SQLException {}

    @Override
    public List<Result> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<Result> res = new ArrayList<Result>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String typeTest = rs.getString("typeTest");
                float percent = rs.getFloat("percent");
                Date date = rs.getDate("date");
                int idUser = rs.getInt("idUser");
                Result result = new Result(id);
                result.setTypeTest(typeTest );
                result.setPercent(percent);
                result.setDate(date);
                result.setIdUser(idUser);
                res.add(result);
            }
            conn.commit();
            return res;
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
    public int insert(Result result) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, result.getTypeTest());
            ps.setFloat(2, result.getPercent());
            ps.setDate(3, (java.sql.Date) result.getDate());
            ps.setInt(4, result.getIdUser());
            ps.executeUpdate();
            //Получение первичного ключа сгенерированного базой
            resultSet = ps.getGeneratedKeys();
            while (resultSet.next())
                res = resultSet.getInt(1);

            conn.commit();
            return res;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + SELECT_ALL_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int[] insert(List<Result> results) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res[] = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for (Result result : results) {
                ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, result.getTypeTest());
                ps.setFloat(2, result.getPercent());
                ps.setDate(3, (java.sql.Date) result.getDate());
                ps.setInt(4, result.getIdUser());
                ps.addBatch();
                res = ps.executeBatch();
            }

            conn.commit();
            return res;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + SELECT_ALL_SQL + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }
}
