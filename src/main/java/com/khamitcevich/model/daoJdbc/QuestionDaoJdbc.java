package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Category;
import com.khamitcevich.model.entitiesDB.Question;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueCategoryException;
import com.khamitcevich.model.exception.NotUniqueQuestionException;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoJdbc implements QuestionDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.question";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.question Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.question (idCategory, body) values (?, ?)";
    public static final String SELECT_BY_NAME_QUESTION = "SELECT id FROM testingmedicaleployees.question WHERE body = ?";

    public QuestionDaoJdbc  () throws SQLException {}

    @Override
    public List<Question> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<Question> result = new ArrayList<Question>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idCategory = rs.getInt("idCategory");
                String body = rs.getString("body");
                Question question = new Question(id);
                question.setIdCategory(idCategory);
                question.setBody(body);
                result.add(question);
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
    public int insert(Question question) throws DBSystemException, SQLException, NotUniqueQuestionException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            if (existWithQuestion0(conn, question.getBody())) {
                throw new NotUniqueQuestionException("Question '" + question.getBody() + "'");
            }

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, question.getIdCategory());
            ps.setString(2, question.getBody());
            ps.executeUpdate();
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
    public int[] insert(List<Question> questions) throws DBSystemException, SQLException, NotUniqueQuestionException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res[] = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for (Question question : questions) {
                if (existWithQuestion0(conn, question.getBody())) {
                    throw new NotUniqueQuestionException("Question '" + question.getBody() + "'");
                }

                ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, question.getIdCategory());
                ps.setString(2, question.getBody());
                ps.addBatch();
                res = ps.executeBatch();
            }

            conn.commit();
            return res;
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

    private boolean existWithQuestion0(Connection conn, String body) throws SQLException{
        PreparedStatement st = conn.prepareStatement(SELECT_BY_NAME_QUESTION);
        st.setString(1, body);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
