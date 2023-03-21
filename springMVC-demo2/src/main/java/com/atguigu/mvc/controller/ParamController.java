package com.atguigu.mvc.controller;

import com.atguigu.mvc.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Auther:luosheng
 * @Date:2022/12/06/19:35
 * @Description:
 */
@Controller
public class ParamController {

    /*通过ServletAPI获取*/
    @RequestMapping("testServletAPI")
    public String ServletAPI(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("name:" + name + ",age" + age);
        return "success";
    }

    /*通过控制器方法的形参获取请求参数*/
    @RequestMapping("/testParam")
    public String Param(String username, String password) {
        System.out.println("username:" + username + ",password" + password);
        return "success";
    }

    /*通过控制器方法的形参获取请求参数 之接收一个数组参数*/
    @RequestMapping("/testParam2")
    public String Param2(String username, String password, String[] hobby) {
        //若请求参数中出现多个同名的请求参数，可以在控制器方法的形参位置设置字符串类型或字符串数组类接收此参数
        //若使用字符串类型的形参，最终结果 为 请求参数的每一个值之间使用逗号进行拼接 ，数组就是简单的数组格式
        System.out.println("username:" + username + ",password:" + password + "hobby:" + Arrays.toString(hobby));
        return "success";
    }
    /*@RequestParam*/

    @RequestMapping("/testParam3")
    public String Param3(
            @RequestParam(value = "user_name", required = false, defaultValue = "hehe") String username,
            String password,
            String[] hobby
    ) {
        System.out.println("username:" + username + ",password:" + password + "hobby:" + Arrays.toString(hobby));
        return "success";
    }

    @RequestMapping(value = "/testpojo", method = RequestMethod.POST)
    public String Pojo(User user) {
        System.out.println(user);
        return "success";
    }
}
