package com.mybatis.controller;

import com.mybatis.bean.User;
import com.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/2 15:14
 * @Description:
 */
//@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userServicel;

}
