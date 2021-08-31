package com.sox.webapp.validate;

import com.sox.webapp.model.ResponseDataSet;
import org.springframework.validation.BindingResult;


public abstract class Validator {
    protected BindingResult result;

    public Validator(BindingResult result) {
        this.result = result;
    }

    public abstract ResponseDataSet<Object> getResult();
}
