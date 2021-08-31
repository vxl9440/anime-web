package com.sox.webapp.factory;

import com.sox.webapp.model.ResponseDataSet;

public class ReturnResultFactory {
    public static ResponseDataSet<Object> build(int status, String message, Object data){
        return new ResponseDataSet<>(status,message,data);
    }
}
