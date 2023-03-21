package com.atguigu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:luosheng
 * @Date:2022/12/05/20:10
 * @Description:
 */
@Controller
public class TestController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/a?a/testAnt")
    public String success() {
        return "success";
    }

    @RequestMapping("/testPath/{id}")
    public String testPath(@PathVariable("id") Integer id) {
        System.out.println("id:" + id);
        return "success";
    }

    @RequestMapping("/testPath1/{id}/{username}")
    public String testPath2(@PathVariable("id")Integer id,@PathVariable("username")String username){
        System.out.println("id:"+id+",username:"+username);
        return "success";
    }
    @RequestMapping("/test_param.html")
    public String param() {
        return "test_param";
    }
}
