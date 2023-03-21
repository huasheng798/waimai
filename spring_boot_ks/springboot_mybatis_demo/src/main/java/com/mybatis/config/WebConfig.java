package com.mybatis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:luosheng
 * @Date:2023/3/9 16:03
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器  ,对所有访问路径进行拦截,并设置拦截路径
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                //设置不被拦截的路径,一个是首页，一个是静态资源
                .excludePathPatterns("/user/login","/user/c","/layui/**","/jquery-3.3.1.min.js/**","/bootstrap.min.css/**"
                ,"/bootstrap.min.js/**");
    }
}
