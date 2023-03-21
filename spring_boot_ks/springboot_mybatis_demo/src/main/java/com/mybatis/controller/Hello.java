package com.mybatis.controller;

import com.mybatis.bean.Book;
import com.mybatis.bean.User;
import com.mybatis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/6 9:01
 * @Description:
 */
@Controller
public class Hello {
    @Autowired
    private BookService bookService;


    @RequestMapping("/hello")
    public String hello(Model model, HttpSession session) {
        model.addAttribute("id", 12313);
        User user = new User();
        user.setUserName("aa");
        user.setEmail("请求权");
        model.addAttribute("user", user);
        session.setAttribute("uid", "这是session");



        List<Object> list = new ArrayList<>();
        list.add("红色");
        list.add("蓝色");
        list.add("白色");
        list.add("紫色");
        model.addAttribute("list",list);
        return "hello";
    }


}
