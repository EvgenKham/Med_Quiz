package com.khamitcevich.model.pools;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryDbcp implements ConnectionFactory {
    private static final String URL_JDBC = "jdbc:mysql://localhost:3306/testingmedicaleployees" +
            "?verifyServerCertificate=false" +
            "&useSSL=false" +
            "&requireSSL=false" +
            "&useLegacyDatetimeCode=false" +
            "&amp" +
            "&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private final BasicDataSource dataSource;

    public ConnectionFactoryDbcp() {

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DRIVER_CLASS);
        ds.setUsername(LOGIN);
        ds.setPassword(PASSWORD);
        ds.setUrl(URL_JDBC);
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
