package com.khamitcevich.model.daoJdbc;

import com.khamitcevich.model.entitiesDB.Category;
import com.khamitcevich.model.exception.*;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    public List<Category> selectAll () throws DBSystemException, SQLException;

    public int deleteById (int id ) throws DBSystemException, SQLException;

    public int insert(Category category) throws DBSystemException, SQLException, NotUniqueCategoryException;

    public int [] insert(List<Category> categories) throws DBSystemException, SQLException, NotUniqueCategoryException;
}
