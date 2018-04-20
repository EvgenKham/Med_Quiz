package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Answer;
import com.khamitcevich.model.entitiesDB.User;
import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoJdbc implements AnswerDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.answer";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.answer Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.answer (idQuestion, version, isRight) values (?, ?, ?)";

    public AnswerDaoJdbc  () throws SQLException {}

    @Override
    public List<Answer> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<Answer> result = new ArrayList<Answer>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idQuestion = rs.getInt("idQuestion");
                String version = rs.getString("version");
                String isRight = rs.getString("isRight");
                Answer answer = new Answer(id);
                answer.setIdQuestion(idQuestion);
                answer.setVersion(version);
                answer.setIsRight(isRight);
                result.add(answer);
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
    public int insert(Answer answer) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, answer.getIdQuestion());
            ps.setString(2, answer.getVersion());
            ps.setString(3, answer.getIsRight());
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
    public int[] insert(List<Answer> answers) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        int count [] = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for (Answer answer: answers) {
                ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, answer.getIdQuestion());
                ps.setString(2, answer.getVersion());
                ps.setString(3, answer.getIsRight());
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
}
