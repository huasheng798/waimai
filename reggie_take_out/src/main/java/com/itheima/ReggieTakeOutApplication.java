package com.itheima;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//lombok提供的一个日志工具用于代码调试
@Slf4j

@SpringBootApplication
//在SpringBootApplication上使用@ServletComponentScan注解后，
//Servlet、Filter、Listener可以直接通过@WebServlet、@WebFilter、@WebListener注解自动注册，无需其他代码。
@ServletComponentScan
//开启支持事务的注解
@EnableTransactionManagement
@EnableCaching  //开启Spring Cache注解方式的缓存功能  然后到SetmealController中list配置相关功能
public class ReggieTakeOutApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeOutApplication.class, args);
        log.info("项目启动成功...");
    }

}
