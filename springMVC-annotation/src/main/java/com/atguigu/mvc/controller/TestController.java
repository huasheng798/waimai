package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:luosheng
 * @Date:2022/12/12/15:12
 * @Description:
 */
@Controller
public class TestController {

    @RequestMapping("/")
    public String testIndex() {
        return "index";
    }
}
