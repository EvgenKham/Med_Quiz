package com.khamitcevich.model.pools;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryC3p0 implements ConnectionFactory {

    @Override
    public Connection newConnection() throws SQLException {
        return null;
    }

    @Override
    public void close() throws SQLException {

    }
}
