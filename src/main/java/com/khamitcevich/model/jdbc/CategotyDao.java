package com.khamitcevich.model.jdbc;

import com.khamitcevich.model.entitiesDB.Category;
import com.khamitcevich.model.exception.DBSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CategotyDao extends Dao<Category> {

    List<Category> selectAll () throws DBSystemException, SQLException;

    Category selectById (int id) throws DBSystemException, SQLException;

    int insertOne(Category category) throws DBSystemException, SQLException;

    List<Integer> insertList(List<Category> result) throws DBSystemException, SQLException;

    boolean update(Category category) throws DBSystemException, SQLException;
}
