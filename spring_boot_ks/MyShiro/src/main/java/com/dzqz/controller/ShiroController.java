package com.dzqz.controller;

import com.dzqz.bean.User;
import com.dzqz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:luosheng
 * @Date:2023/3/10 15:10
 * @Description:
 */
@Controller
@Slf4j
public class ShiroController {

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public String add() {
        return "add";
    }

    @RequestMapping("/update")
    public String update() {
        return "update";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/dologin")
    public String dologin(String username, String password, Model model) {
        //建立一个令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        //获取shiro
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            //用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }


        return "index";
    }

    //无权限时访问
    @RequestMapping("/unau")
    public String unau() {
        return "unau";
    }

    //访问注册页面

    @RequestMapping("/newenroll")
    public String newenroll() {
        return "enroll";
    }
    //实现 注册功能

    @RequestMapping("/enroll")
    public String enroll(User user) {
        log.info("当前注册功能接收实体,{}",user);
        String password = user.getPassword();
        Md5Hash md5Hash = new Md5Hash(password, user.getUserName(), 3);
        user.setPassword(md5Hash.toString());
        try {
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return "enroll";
        }

        return "login";
    }


}
