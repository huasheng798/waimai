package com.atguigu.mvc.controller;

import com.atguigu.mvc.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Auther:luosheng
 * @Date:2022/12/11/13:01
 * @Description:
 */
@Controller
public class HttpController {

    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        try {
            System.out.println("requestBody" + URLDecoder.decode(requestBody, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("s");
        return "success";
    }


    @RequestMapping("/testResponseUser")
    @ResponseBody
    public User testResponseUser() {
//不能用 因为我们浏览器 怎么能接收我们一个java对象，！！！这时候就用到json,才可以使用！！！
        //我们导入依赖后直接 就重新部署可以使用 还有几个步骤我们已经做过了 详细实现步骤看笔记
        return new User(1001, "admin", "123");
    }
}
