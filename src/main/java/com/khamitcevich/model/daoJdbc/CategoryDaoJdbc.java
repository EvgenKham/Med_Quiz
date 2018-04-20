package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Category;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueCategoryException;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.category";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.category Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.category (id, nameCategory) values (?, ?)";
    public static final String SELECT_BY_NAME_CATEGORY = "SELECT id FROM testingmedicaleployees.category WHERE nameCategory = ?";

    public CategoryDaoJdbc () throws SQLException {}

    @Override
    public List<Category> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<Category> res = new ArrayList<Category>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nameCategory = rs.getString("nameCategory");
                Category category = new Category(id);
                category.setName(nameCategory);
                res.add(category);
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
    public int insert(Category category) throws DBSystemException, SQLException, NotUniqueCategoryException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            if (existWithCategory0(conn, category.getName())) {
                throw new NotUniqueCategoryException("Category '" + category.getName() + "'");
            }

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
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
    public int[] insert(List<Category> categories) throws DBSystemException, SQLException, NotUniqueCategoryException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res[] = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for (Category category : categories) {

                if (existWithCategory0(conn, category.getName())) {
                    throw new NotUniqueCategoryException("Category '" + category.getName() + "'");
                }
                ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, category.getName());
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

    private boolean existWithCategory0(Connection conn, String name) throws SQLException{
        PreparedStatement st = conn.prepareStatement(SELECT_BY_NAME_CATEGORY);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }
}
