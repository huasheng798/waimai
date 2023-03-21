package com.atguigu.config;

import com.atguigu.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/3/11 17:05
 * @Description:
 */
@Configuration
public class shiroConfig {
    //    @Autowired
//    private MyRealm myRealm;
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    //配置 SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        //1 创建 defaultWebSecurityManager 对象
//        DefaultWebSecurityManager defaultWebSecurityManager = new
//                DefaultWebSecurityManager();
        DefaultWebSecurityManager defaultWebSecurityManager = new
                DefaultWebSecurityManager();
        //2 创建加密对象，并设置相关属性
        HashedCredentialsMatcher matcher = new
                HashedCredentialsMatcher();
        //2.1 采用 md5 加密
        matcher.setHashAlgorithmName("md5");
        //2.2 迭代加密次数  暂时不需要
        //matcher.setHashIterations(3);
        //3 将加密对象存储到 myRealm 中
        myRealm().setCredentialsMatcher(matcher);
        //4 将 myRealm 存入 defaultWebSecurityManager 对象
        defaultWebSecurityManager.setRealm(myRealm());
        //4.5 设置 rememberMe

        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());

        //5 返回
        return defaultWebSecurityManager;
    }

    //cookie 属性设置
    public SimpleCookie rememberMeCookie(){
        //cooke名字
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //设置跨域
        //cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }
    //创建 Shiro 的 cookie 管理对象
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new
                CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());

        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }




    //3.配置shiro过滤器
    @Bean
    public ShiroFilterFactoryBean filterFactoryBean() {
        //创建shiro过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给过滤器谁知股那里工具
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());
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
        map.put("/", "anon");
        map.put("/**","authc");
        //设登录链接
        shiroFilterFactoryBean.setLoginUrl("/user/main");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
//添加存在用户的过滤器（rememberMe）
      //  shiroFilterFactoryBean.se("/**","user");

        return shiroFilterFactoryBean;
    }







//    //配置 Shiro 内置过滤器拦截范围(这个有问题 他没有配置拦截谁，我觉得他这个默认没招到)
//    @Bean
//    public DefaultShiroFilterChainDefinition
//    shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition definition = new
//                DefaultShiroFilterChainDefinition();
//        //设置不认证可以访问的资源
//        definition.addPathDefinition("/index", "anon");
//        //给过滤器谁知股那里工具
//        definition.setSecurityManager(defaultWebSecurityManager());
//        definition.addPathDefinition("/user/main", "anon");
////        //设置需要进行登录认证的拦截范围
//        definition.addPathDefinition("/**", "authc");
//        return definition;
//    }

//    @Bean
//    public FilterRegistrationBean registration(RolesAuthorizationFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
//        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
//        registration.setEnabled(false);
//        return registration;
//    }

}
