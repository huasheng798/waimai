package com.mybatis.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:luosheng
 * @Date:2023/3/8 10:37
 * @Description:处理异常信息
 */
@ControllerAdvice //处理异常的注解
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Object defaultExceptionHandler(Exception e) {
        e.printStackTrace();//打印异常
        return "出现异常了";
    }

    //访问404处理
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory>
    silingsi(){
        return ( factory -> {
            //NOT_FOUND就是404 设置错误页面
            ErrorPage error404Page=new ErrorPage(HttpStatus.NOT_FOUND,"/404");
            //把错误页面加载到工程里
            factory.addErrorPages(error404Page);
        });
    }
}
