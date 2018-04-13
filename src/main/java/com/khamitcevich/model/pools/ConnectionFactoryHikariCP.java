package com.khamitcevich.model.pools;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryHikariCP implements ConnectionFactory {
    private static final String URL_JDBC = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    private HikariDataSource dataSource = null;

    public ConnectionFactoryHikariCP() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(DRIVER_CLASS);
        ds.setUsername(LOGIN);
        ds.setPassword(PASSWORD);
        ds.setJdbcUrl(URL_JDBC);
        
        dataSource = ds;
    }


    @Override
    public Connection newConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void close() throws SQLException {
        dataSource.close();
    }
}
