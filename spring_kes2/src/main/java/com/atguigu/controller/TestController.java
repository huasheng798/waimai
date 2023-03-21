package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author:luosheng
 * @Date:2023/2/16 17:18
 * @Description:
 */
@Controller
public class TestController {

    @RequestMapping("/register")
    public ModelAndView login(ModelAndView mav) {
        mav.addObject("name", "admin");
        mav.setViewName("register");

        return mav;
    }

}
