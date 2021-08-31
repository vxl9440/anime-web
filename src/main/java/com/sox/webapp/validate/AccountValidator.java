package com.sox.webapp.validate;

import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.factory.ReturnResultFactory;
import com.sox.webapp.model.Account;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.util.Constant;
import org.springframework.validation.BindingResult;

public class AccountValidator extends Validator{
    private final Account account;
    public AccountValidator(BindingResult result,Account account) {
        super(result);
        this.account = account;
    }

    @Override
    public ResponseDataSet<Object> getResult() {
        if(result.hasErrors()){
            throw new DataFormatInvalidException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ReturnResultFactory.build(Constant.SUCCESS_CODE,"SUCCESS",this.account);
    }
}
