package com.sox.webapp.exception;

public class SqlFailOperationException extends RuntimeException{

    public SqlFailOperationException(String message){
        super(message);
    }
}
