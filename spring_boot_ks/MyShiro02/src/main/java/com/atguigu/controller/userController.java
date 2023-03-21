package com.atguigu.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

/**
 * @Author:luosheng
 * @Date:2023/3/11 17:26
 * @Description:
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class userController {

//    @GetMapping("/index")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/main")
    public String userLogin(String name,
                            String pwd, HttpServletRequest request) {
        log.info("当前输入用户名{},当前输入密码{}", name, pwd);

        //1 获取 Subject 对象
        Subject subject = SecurityUtils.getSubject();
        //2 封装请求数据到 token 对象中
        AuthenticationToken token = new UsernamePasswordToken(name, pwd);
        //3 调用 login 方法进行登录认证
        try {
            subject.login(token);
            request.getSession().setAttribute("user", token.getPrincipal().toString());
            return "main";
        } catch (UnknownAccountException uae) {

            //用户名错误
            return "用户名错误";
        } catch (IncorrectCredentialsException ice) {
            //密码错误
            return "密码错误";
        } catch (LockedAccountException lae) {
            //account for that username is locked - can't login.  Show them a message?
            return "a";
        } catch (AuthenticationException ae) {
            //unexpected condition - error?
            return "b";
        }
    }
}

