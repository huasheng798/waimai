package com.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:luosheng
 * @Date:2023/3/8 11:20
 * @Description:
 */
@Controller
public class BaseController {

    @RequestMapping("/404")
    public String cuow() {
        return "404";
    }
}
