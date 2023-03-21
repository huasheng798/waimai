package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:luosheng
 * @Date:2022/12/05/20:51
 * @Description:
 */
@RequestMapping("/hello")
@Controller
public class RequestMappingController {
    @RequestMapping("/testRequestMapping")
    public String success(){
        return "success";
    }

}
