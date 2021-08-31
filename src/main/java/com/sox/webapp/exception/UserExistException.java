package com.sox.webapp.exception;


public class UserExistException extends RuntimeException{

    public UserExistException(String message){
        super(message);
    }
}
