package com.sox.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sox.webapp.exception.DataFormatInvalidException;
import com.sox.webapp.exception.SqlFailOperationException;
import com.sox.webapp.exception.UserExistException;
import com.sox.webapp.mapper.AccountMapper;
import com.sox.webapp.model.Account;
import com.sox.webapp.model.ResponseDataSet;
import com.sox.webapp.service.AccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public Account getAccountByUsername(String username) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return accountMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> insertAccount2(ResponseDataSet<Object> dataSet) {
        Account account = (Account) dataSet.getData();
        if(this.getAccountByUsername(account.getUsername()) != null){
            throw new UserExistException("该用户名已存在");
        }
        account.setRole("SLAVE");
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        if(accountMapper.insert(account) == 0){
            throw new SqlFailOperationException("服务器错误");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> insertAccount(ResponseDataSet<Object> dataSet) {
        Account account = (Account) dataSet.getData();
        if(this.getAccountByUsername(account.getUsername()) != null){
            throw new UserExistException("该用户名已存在");
        }
        account.setRole("CIVILIAN");
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        if(accountMapper.insert(account) == 0){
            throw new SqlFailOperationException("服务器错误");
        }
        dataSet.setData(null);
        return dataSet;
    }

    @Override
    @Transactional
    public ResponseDataSet<Object> updateAccount(ResponseDataSet<Object> dataSet) {
        Account account = (Account) dataSet.getData();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        account.setUsername(username);
        String oldPassword = accountMapper.selectById(username).getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(account.getOldPassword(),oldPassword)){
            throw new DataFormatInvalidException("旧密码不正确");
        }
        UpdateWrapper<Account> wrapper = new UpdateWrapper<>();
        wrapper.eq("username",account.getUsername()).
                set("password",encoder.encode(account.getPassword()));
        if(accountMapper.update(null,wrapper) == 0){
            throw new SqlFailOperationException("服务器错误");
        }
        dataSet.setData(null);
        return dataSet;
    }
}
