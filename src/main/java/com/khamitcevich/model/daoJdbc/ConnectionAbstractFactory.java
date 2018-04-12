package com.khamitcevich.model.daoJdbc;

import java.sql.SQLException;

public class ConnectionAbstractFactory {
    public static enum FactoryType{C3P0, DBCP, HIKARICP};

    private static FactoryType currentType  = FactoryType.DBCP;
//    private static List<ConnectionFactory> allFactoryeis = new LinkedList<ConnectionFactory>();

    public static synchronized void setType(FactoryType type) {
        currentType = type;
    }

    public synchronized static ConnectionFactory newConnectionFactory() throws SQLException {
        ConnectionFactory result;

        switch (currentType) {
            case C3P0:
                result = new ConnectionFactoryC3p0();
                break;

            case DBCP:
                result = new ConnectionFactoryDbcp();
                break;

            case HIKARICP:
                result = new ConnectionFactoryHikariCP();
                break;
            default:
                throw new RuntimeException("Never here! Now only");
        }
        return result;
    }
}
