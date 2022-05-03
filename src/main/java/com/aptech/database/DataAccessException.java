package com.aptech.database;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String msg){
        super(msg);
    }
}