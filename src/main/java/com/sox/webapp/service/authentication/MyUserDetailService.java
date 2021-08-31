package com.sox.webapp.service.authentication;


import com.sox.webapp.model.Account;
import com.sox.webapp.service.impl.AccountServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final AccountServiceImpl accountService;

    public MyUserDetailService(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用usersMapper方法，根据用户名查询数据库
        Account account = accountService.getAccountByUsername(username);
        //判断
        if(account == null) {//数据库没有用户名，认证失败
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if(!"SLAVE".equals(account.getRole())){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> auths =
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+account.getRole());
        //从查询数据库返回users对象，得到用户名和密码，返回
        return new User(account.getUsername(),account.getPassword(),auths);
    }
}
