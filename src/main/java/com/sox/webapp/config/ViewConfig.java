/*
 * @version: V1.0
 * @author: Miaoxin Li
 * @className: ViewConfig
 * @packageName: com.sox.webapp.config
 * @description: A class to configure view controller
 * @date: 2021-07-21
 */
package com.sox.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/user/index.html");
        registry.addViewController("/admin/adminCreateAnime").setViewName("/admin/adminCreateAnime.html");
        registry.addViewController("/admin/adminRewriteAnime").setViewName("/admin/adminRewriteAnime.html");
        registry.addViewController("/admin/adminAnimeCategory").setViewName("/admin/adminAnimeCategory.html");
        registry.addViewController("/admin/adminLogin").setViewName("/admin/adminLogin.html");
        registry.addViewController("/admin/adminSpace").setViewName("/admin/adminSpace.html");
        registry.addViewController("/admin/adminAnimeRelation").setViewName("/admin/adminAnimeRelation.html");
        registry.addViewController("/index").setViewName("/user/index.html");
        registry.addViewController("/category").setViewName("/user/category.html");
        registry.addViewController("/search").setViewName("/user/search.html");
        registry.addViewController("/detail").setViewName("/user/detail.html");
        registry.addViewController("/login").setViewName("/user/login.html");
        registry.addViewController("/profile").setViewName("/user/profile.html");
        registry.addViewController("/play").setViewName("/user/play.html");
        registry.addViewController("/500").setViewName("/error/500.html");
        registry.addViewController("/admin/adminCustom").setViewName("/admin/adminCustom.html");
    }
}
