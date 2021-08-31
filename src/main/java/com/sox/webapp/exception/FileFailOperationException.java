package com.sox.webapp.exception;

public class FileFailOperationException extends RuntimeException{
    public FileFailOperationException(String message){
        super(message);
    }
}
