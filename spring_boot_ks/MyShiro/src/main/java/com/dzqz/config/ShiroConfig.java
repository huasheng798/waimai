package com.dzqz.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/3/10 15:28
 * @Description:
 */

@Configuration
public class ShiroConfig {
    //1.realm,这个MyRealm是自定义的
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        //告诉myRealm加密方式        这里hashedCredentialsMatcher是我们下面写的配置
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }
    //2.基于Web的securityManager

    @Bean
    public DefaultWebSecurityManager securityManager() {
        //创建defaultWebSecurityManager对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //创建加密对象，并设置相关属性
//        HashedCredentialsMatcher matcher = new
//                HashedCredentialsMatcher();
//        //采用md5加密
//        matcher.setHashAlgorithmName("md5");
//        //将加密对象存储到myRealm中
//        myRealm().setCredentialsMatcher(matcher);
        //将myRealm存入defaultWebSecurityManager
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    //3.配置shiro过滤器
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean() {
        //创建shiro过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给过滤器谁知股那里工具
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //设置过滤请求
        Map<String, String> map = new HashMap<>();
        /*
        建立map请求权限映射
        key是要过滤的请求，
        shiro:内置过滤器
        authc:必须身份认证才能方法问
        anon:无需认证就可以访问
        perms:拥有对某个资源的权限才可以访问
        role:拥有某个角色的权限才可以认证
        */
        //其中perms就是我们对应实体类中的perms中数据库查出来的权限，也属于自定义权限
        //我们可以在myRealm中的doGetAuthorizationInfo方法看我们相关配置
        map.put("/add", "perms[add]");
        map.put("/update", "perms[update]");
//        map.put("/", "authc");
        map.put("/", "anon");
        //无权限时访问
        shiroFilterFactoryBean.setUnauthorizedUrl("/unau");

        //设登录链接
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }
    //加密

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //设置加密算法
        matcher.setHashAlgorithmName("md5");
        //设置加密次数
        matcher.setHashIterations(3);
        //把信息给到myRealm
        return matcher;
    }

    @Bean
    /*配置thymeleaf整合shiro*/
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
