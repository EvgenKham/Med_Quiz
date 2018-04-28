package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T> {
    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

     protected AbstractDao() throws SQLException {
    }

    protected Connection getSerializableConnection () throws DBSystemException {
        try {
            Connection conn = factory.newConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new DBSystemException("Can't create connection", e);
        }
    }

    protected List<T> selectAll (String sql, Manipulation<T> extractor)  throws DBSystemException{
        Connection conn = getSerializableConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            List<T> result = new ArrayList<T>();
            while (rs.next()) {
                T record = extractor.extractOne(rs);
                result.add(record);
            }
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + sql + "'" + e);
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(conn);
        }
    }

    protected T selectById (String sql, Manipulation<T> extractor, int id) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery(sql);
            T result = null;
            while (rs.next()) {
                result = extractor.extractOne(rs);
            }
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + sql + "'" + e);
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(preparedStatement);
            JdbcUtils.closeQuietly(conn);
        }
    }

    //Return integer '1' if deleted, else '0'
    protected int deleteById (String sql, int id ) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            conn.commit();
            return result;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + sql + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    //Return the integer primary_key that generated the database
    protected int insertOne(Connection conn, T newEntity, Manipulation<T> add, String sql) throws DBSystemException, SQLException {
        PreparedStatement ps = null;
        int result;
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            result = add.addOne(ps, newEntity);
            conn.commit();
            return result;
        }
        catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + sql + "'" + e);
        }
        finally {
            JdbcUtils.closeQuietly(ps);
            JdbcUtils.closeQuietly(conn);
        }
    }

    protected boolean update(T newEntity) throws DBSystemException, SQLException {
        //goto
        return false;
    }

}
