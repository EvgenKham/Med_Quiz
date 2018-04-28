package com.khamitcevich.model;

import com.khamitcevich.model.entitiesDB.Category;
import com.khamitcevich.model.exception.DBSystemException;
import com.khamitcevich.model.exception.NotUniqueCategoryException;
import com.khamitcevich.model.jdbc.AbstractDao;
import com.khamitcevich.model.jdbc.CategotyDao;
import com.khamitcevich.model.jdbc.ManipulationCategoty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategotyDaoJdbc extends AbstractDao<Category> implements CategotyDao {

    private static final String SELECT_ALL_SQL = "Select * From testingmedicaleployees.category";
    private static final String SELECT_BY_ID_SQL = "Select * From testingmedicaleployees.category Where id = ?";
    private static final String DELETE_BY_ID_SQL = "Delete From testingmedicaleployees.category Where id = ?";
    private static final String INSERT_SQL = "Insert into testingmedicaleployees.category (id, nameCategory) values (?, ?)";
    private static final String SELECT_BY_NAME_CATEGORY = "SELECT id FROM testingmedicaleployees.category WHERE nameCategory = ?";

    public CategotyDaoJdbc() throws SQLException {
    }

    @Override
    public List<Category> selectAll() throws DBSystemException, SQLException {
        return selectAll(SELECT_ALL_SQL, new ManipulationCategoty());
    }

    @Override
    public Category selectById (int id) throws DBSystemException, SQLException{
        return selectById(SELECT_BY_ID_SQL, new ManipulationCategoty(), id);
    }

    @Override
    public int deleteById(int id) throws DBSystemException, SQLException {
        return deleteById(DELETE_BY_ID_SQL, id);
    }

    @Override
    public int insertOne(Category category) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();

        if(uniqueData(conn, category))
            return insertOne(conn, category, new ManipulationCategoty(), INSERT_SQL);
        else
            return 0;
    }

    @Override
    public List<Integer> insertList(List<Category> categories) throws DBSystemException, SQLException {
        Connection conn = getSerializableConnection();
        List<Integer> result = new ArrayList<Integer>();

        for (Category category : categories) {
            if(uniqueData(conn, category))
                result.add(insertOne(conn, category, new ManipulationCategoty(), INSERT_SQL));
        }
        return result;
    }

    @Override
    public boolean update(Category category) throws DBSystemException, SQLException {
        return false;
    }

    private boolean existWithCategory0(Connection conn, String name) throws SQLException{
        PreparedStatement st = conn.prepareStatement(SELECT_BY_NAME_CATEGORY);
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

    private boolean uniqueData(Connection conn, Category category) throws DBSystemException, SQLException {
        try {
            if (existWithCategory0(conn, category.getName())) {
                throw new NotUniqueCategoryException("Category '" + category.getName() + "'");
            }
            return true;
        } catch (NotUniqueCategoryException e) {
            e.printStackTrace();
            return  false;
        }
    }
}
