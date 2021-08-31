package com.sox.webapp.service;


import com.sox.webapp.model.Account;
import com.sox.webapp.model.ResponseDataSet;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountService {
    Account getAccountByUsername(String username);
    ResponseDataSet<Object> insertAccount2(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> insertAccount(ResponseDataSet<Object> dataSet);
    ResponseDataSet<Object> updateAccount(ResponseDataSet<Object> dataSet);
}
