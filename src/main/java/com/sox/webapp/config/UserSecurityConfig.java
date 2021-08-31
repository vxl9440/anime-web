/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: UserSecurityConfig
 * @packageName: com.sox.webapp.config
 * @description: A class to configure spring security for user page
 * @date: 2021-07-20
 */
package com.sox.webapp.config;

import com.sox.webapp.service.authentication.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    /** userDetailService **/
    private final MyUserDetailService myUserDetailsService;
    /** dataSource **/
    private final DataSource dataSource;

    /**
     *
     * @param myUserDetailsService injected from bean container
     * @param dataSource injected from bean container
     */
    public UserSecurityConfig(MyUserDetailService myUserDetailsService, DataSource dataSource) {
        this.myUserDetailsService = myUserDetailsService;
        this.dataSource = dataSource;
    }

    /**
     * set datasource for PersistentTokenRepository, make it able to access database
     * @return a newly-created PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    /**
     * configure security policies
     * @param http configure
     * @throws Exception throw exception when error occur
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/userLoginVerify")
                .successForwardUrl("/index")
                .failureForwardUrl("/loginFail")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().authorizeRequests()
                .antMatchers("/","/index","/category/**","/detail/**","/search","/signup","/moreComment","/play/**")
                .permitAll()
                .anyRequest().authenticated().
                and().rememberMe(). // remember me
                tokenRepository(persistentTokenRepository()).  // data source
                tokenValiditySeconds(15552000).   // cookie time (second)
                userDetailsService(myUserDetailsService);  // cookie authentication service
        http.csrf().disable();
    }

    /**
     * make filter to ignore static resource
     * @param web configure
     * @throws Exception throw exception when error occur
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }

}
