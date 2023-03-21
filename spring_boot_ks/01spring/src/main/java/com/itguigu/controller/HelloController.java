package com.itguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:luosheng
 * @Date:2023/2/28 8:58
 * @Description:
 */
@Controller
public class HelloController {

    @GetMapping
    @ResponseBody
    public String hello() {
        return "Hello,world";
    }

    @ResponseBody
    @PostMapping
    public String tianqi(){
        return "今天天气真好";
    }

}
