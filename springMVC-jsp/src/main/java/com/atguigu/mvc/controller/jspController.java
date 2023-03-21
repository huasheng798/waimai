package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:luosheng
 * @Date:2022/12/09/10:53
 * @Description:
 */
@Controller
public class jspController {
    @RequestMapping("/success")
    public String testsuccess(){
        return "success";
    }
}
