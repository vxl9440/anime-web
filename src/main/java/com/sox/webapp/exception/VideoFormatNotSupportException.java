package com.sox.webapp.exception;

public class VideoFormatNotSupportException extends RuntimeException{
    public VideoFormatNotSupportException(String message){
        super(message);
    }
}
