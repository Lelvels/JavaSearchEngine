package com.aptech.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class ConnectionHandler {
    private static final ConnectionHandler instance = new ConnectionHandler();
    private ConnectionHandler(){}
    public static ConnectionHandler getInstance(){
        return instance;
    }

    // JDBC driver name and database URL
    private final String DB_URL = "jdbc:mysql://localhost/searchengine?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    private final String USER = "root";
    private final String PASS = "";

    public Optional<Connection> getConnection(){
        //STEP 3: Open a connection
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return Optional.of(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    public void closeConnection(Connection conn) {
        try{
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
