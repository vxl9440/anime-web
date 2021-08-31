/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: AdminAccountController
 * @packageName: com.sox.webapp.controller.admin
 * @description: A class to handle http requests and responses for admin account service
 * @date: 2021-07-21
 */
package com.sox.webapp.controller.admin;

import com.sox.webapp.model.Account;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.impl.AccountServiceImpl;
import com.sox.webapp.validate.AccountValidator;
import com.sox.webapp.validate.Validator;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController // all are response body
public class AdminAccountController {

    /** account service **/
    private final AccountServiceImpl accountService;

    /**
     * @param accountService injected from bean container
     */
    public AdminAccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    /**
     * do account creation service
     * @param account data from frontend, contain username and password
     * @param result validation result for account parameter
     * @return a set of data that tell the user whether the operation is success or fail
     */
    @RequestMapping("/admin/createAccount")
    @Secured({"ROLE_GOD"})
    public ResponseDataSet<Object> createAccount(@RequestBody @Valid Account account, BindingResult result){
        Validator validator = new AccountValidator(result,account);
        return accountService.insertAccount(validator.getResult());
    }

    /**
     * do password change service
     * @param account data from frontend, contain username, old password, and new password
     * @param result validation result for account parameter
     * @return a set of data that tell the user whether the operation is success or fail
     */
    @RequestMapping("/admin/updateAccount")
    public ResponseDataSet<Object> updateAccount(@RequestBody @Valid Account account, BindingResult result){
        Validator validator = new AccountValidator(result,account);
        return accountService.updateAccount(validator.getResult());
    }

}
