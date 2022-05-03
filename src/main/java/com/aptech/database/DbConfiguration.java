package com.aptech.database;

public class DbConfiguration {
    public static final String HOST_NAME = "localhost";
    public static final String DB_NAME = "searchengine";
    public static final String DB_PORT = "3306";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final int DB_MIN_CONNECTIONS = 70;
    public static final int DB_MAX_CONNECTIONS = 140;
    // jdbc:mysql://hostname:port/dbname
    public static final String CONNECTION_URL = "jdbc:mysql://" + HOST_NAME + ":" + DB_PORT + "/" + DB_NAME + "?" + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private DbConfiguration() {
        super();
    }
}
