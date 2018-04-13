package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Role;
import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.pools.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.*;

public class RoleDaoJdbc implements RoleDao {

    private final ConnectionFactory factory = ConnectionAbstractFactory.newConnectionFactory();

    public static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.role";
    public static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.role Where id = ?";
    public static final String INSERT_SQL = "Insert into testingmedicaleployees.role (type) values (?)";
    public static final String SELECT_BY_TYPE = "SELECT id FROM testingmedicaleployees.user WHERE type = ?";

    public RoleDaoJdbc() throws SQLException {

    }

    @Override
    public List<Role> selectAll() throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            rs = statement.executeQuery(SELECT_ALL_SQL);
            List<Role> result = new ArrayList<Role>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                Role role = new Role(id);
                role.setType(type);

                result.add(role);
            }
            conn.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + SELECT_ALL_SQL + "'" + e);
        } finally {
            JdbcUtils.closeQuietly(rs);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        Connection conn = factory.newConnection();
        PreparedStatement preparedStatements = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            preparedStatements = conn.prepareStatement(DELETE_BY_ID_SQL);
            preparedStatements.setInt(1, id);
            int result = preparedStatements.executeUpdate();
            conn.commit();
            return result;

        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(conn);
            throw new DBSystemException("Can't execute SQL '" + DELETE_BY_ID_SQL + "'" + e);
        } finally {
            JdbcUtils.closeQuietly(preparedStatements);
            JdbcUtils.closeQuietly(conn);
        }
    }

    @Override
    public int insert(Role role) throws DBSystemException, SQLException, NotUniqueRoleTypeException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        int res = 0;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            if (existWithType0(conn, role.getType())) {
                throw new NotUniqueRoleTypeException("Type '" + role.getType() + "'");
            }

            ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getType());
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
    public int[] insert(List<Role> roles) throws DBSystemException, SQLException, NotUniqueRoleTypeException {
        Connection conn = factory.newConnection();
        PreparedStatement ps = null;
        int count[] = null;
        try {
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);

            for (Role role : roles) {

                if (existWithType0(conn, role.getType())) {
                    throw new NotUniqueRoleTypeException("Type '" + role.getType() + "'");
                }

                    ps = conn.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS);
                    ps.setString(1, role.getType());
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

    private boolean existWithType0 (Connection conn, String type) throws SQLException {
            PreparedStatement st = conn.prepareStatement(SELECT_BY_TYPE);
            st.setString(1, type);
            ResultSet rs = st.executeQuery();
            return rs.next();
        }
}