/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: AdminSecurityConfig
 * @packageName: com.sox.webapp.config
 * @description: A class to configure springSecurity for admin page
 * @date: 2021-07-20
 **/
package com.sox.webapp.config;

import com.sox.webapp.service.authentication.MyUserDetailService2;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter{

    /** userDetailService for authentication **/
    private final MyUserDetailService2 myUserDetailsService;

    /**
     * Constructor
     * @param myUserDetailsService injected from bean
     */
    public AdminSecurityConfig(MyUserDetailService2 myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }


    /**
     * @param auth build authentication rule
     * @throws Exception throw exception when error occur
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * @param http customize security policies
     * @throws Exception throw exception when error occur
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

//        http.exceptionHandling().accessDeniedPage("/error/404");
        http
                .antMatcher("/admin/**")
                .authorizeRequests().anyRequest().hasAnyRole("GOD","CIVILIAN")
                .and().formLogin()
                .loginProcessingUrl("/admin/login")
                .failureForwardUrl("/admin/loginFail")
                .successForwardUrl("/admin/")
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/adminLogin")
                .permitAll();
        http.csrf().disable();

    }

    /**
     * ignore the static resource
     * @param web customize security policies
     * @throws Exception throw exception when error occur
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }
}
