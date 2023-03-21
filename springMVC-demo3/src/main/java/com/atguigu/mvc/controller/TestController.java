package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:luosheng
 * @Date:2022/12/07/21:45
 * @Description:
 */
@Controller
public class TestController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
