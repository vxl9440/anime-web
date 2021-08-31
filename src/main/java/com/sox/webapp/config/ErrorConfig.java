/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: ErrorConfig
 * @packageName: com.sox.webapp.config
 * @description: A class configure HTTP error
 * @date: 2021-07-21
 */
package com.sox.webapp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

    /**
     * redirect to different URLs when different error occur
     * @param registry add errors
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error400 = new ErrorPage(HttpStatus.BAD_REQUEST,"/admin/adminAnimeCategory/all");
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500");
        ErrorPage error403 = new ErrorPage(HttpStatus.FORBIDDEN,"/403");
        registry.addErrorPages(error400);
        registry.addErrorPages(error500);
        registry.addErrorPages(error403);
    }
}
