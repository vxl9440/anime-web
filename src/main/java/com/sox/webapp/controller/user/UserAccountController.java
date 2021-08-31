package com.sox.webapp.controller.user;


import com.sox.webapp.model.Account;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.AccountServiceImpl;
import com.sox.webapp.validate.AccountValidator;
import com.sox.webapp.validate.Validator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserAccountController {

    private final AccountServiceImpl accountService;

    public UserAccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/signup")
    @ResponseBody
    public ResponseDataSet<Object> signup(@RequestBody @Valid Account account, BindingResult result){
        Validator validator = new AccountValidator(result,account);
        return accountService.insertAccount2(validator.getResult());
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public ResponseDataSet<Object> changePassword(@RequestBody @Valid Account account,BindingResult result){
        Validator validator = new AccountValidator(result,account);
        return accountService.updateAccount(validator.getResult());
    }
}
