package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.Role;
import com.khamitcevich.model.exception.*;
import com.khamitcevich.model.jdbc.AbstractDao;
import com.khamitcevich.model.jdbc.ManipulationRole;
import com.khamitcevich.model.jdbc.RoleDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoJdbc extends AbstractDao<Role> implements RoleDao {

    private static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.role";
    private static final String SELECT_BY_ID_SQL = "Select * From testingmedicaleployees.role Where id = ?";
    private static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.role Where id = ?";
    private static final String INSERT_ONE_SQL = "Insert into testingmedicaleployees.role (type) values (?)";
    private static final String SELECT_BY_TYPE_SQL = "SELECT id FROM testingmedicaleployees.user WHERE type = ?";

    public RoleDaoJdbc() throws SQLException {
    }

    @Override
    public List<Role> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationRole());
    }

    @Override
    public Role selectById(int id) throws DBSystemException, SQLException {
        return selectById(SELECT_BY_ID_SQL, new ManipulationRole(), id);
    }

    @Override
    //Return integer '1' if deleted, else '0'
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(Role role) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        if(uniqueData(conn, role))
            return insertOne(conn, role, new ManipulationRole(), INSERT_ONE_SQL);
        else
            return 0;
    }

    @Override
    public List<Integer> insertList(List<Role> roles) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();

        List<Integer> result = new ArrayList<Integer>();

        for(Role role : roles) {
            if(uniqueData(conn, role))
                result.add(insertOne(conn, role, new ManipulationRole(), INSERT_ONE_SQL));
        }
        return result;
    }

    @Override
    public boolean update(Role newEntity) throws DBSystemException, SQLException {
        return false;
    }

    private boolean existWithType0 (Connection conn, String type) throws SQLException {
        PreparedStatement st = conn.prepareStatement(SELECT_BY_TYPE_SQL);
        st.setString(1, type);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean uniqueData(Connection conn, Role role) throws DBSystemException, SQLException {
        try {
            if (existWithType0(conn, role.getType())) {
                throw new NotUniqueRoleTypeException("Type '" + role.getType() + "'");
            }
            return true;
        } catch (NotUniqueRoleTypeException e) {
            e.printStackTrace();
            return  false;
        }
    }
}
