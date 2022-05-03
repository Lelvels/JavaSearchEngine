package com.aptech.database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.aptech.database.DbConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0DataSource {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDriverClass(DbConfiguration.DB_DRIVER);
            cpds.setJdbcUrl(DbConfiguration.CONNECTION_URL);
            cpds.setUser(DbConfiguration.USER_NAME);
            cpds.setPassword(DbConfiguration.PASSWORD);
            cpds.setMinPoolSize(DbConfiguration.DB_MIN_CONNECTIONS);
            cpds.setInitialPoolSize(DbConfiguration.DB_MIN_CONNECTIONS);
            cpds.setMaxPoolSize(DbConfiguration.DB_MAX_CONNECTIONS);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private C3p0DataSource() {
        super();
    }

    public static Connection getConnection() throws SQLException {
        Connection conn =  cpds.getConnection();
        return conn;
    }
}
