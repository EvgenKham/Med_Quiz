package com.khamitcevich.model.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Manipulation<T> {

    public abstract T extractOne (ResultSet rs) throws SQLException;

    public List<T> extractAll (ResultSet rs) throws SQLException {
        List<T> result = new ArrayList<T>();
        while (rs.next()) {
            result.add(extractOne(rs));
        }
        return result;
    }

    public abstract int addOne(PreparedStatement ps, T newEntity) throws SQLException;

    public List<Integer> addAll (PreparedStatement ps, T newEntity) throws SQLException {

        List<Integer> result = new ArrayList<Integer>();
        ResultSet rs = ps.getGeneratedKeys();
        while (rs.next()) {
            result.add(addOne(ps, newEntity));
        }
        return result;
    }

}
