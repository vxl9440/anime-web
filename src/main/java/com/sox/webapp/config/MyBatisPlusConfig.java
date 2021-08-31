/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: MyBatisPlusConfig
 * @packageName: com.sox.webapp.config
 * @description: A class to configure pagination
 * @date: 2021-07-21
 */
package com.sox.webapp.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    /**
     * Inject into bean container
     * @return a newly-created pagination interceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
