package com.atguigu.controller;

import com.atguigu.bean.User;
import com.atguigu.common.LayuiReturn;
import com.atguigu.server.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/23 21:26
 * @Description:
 */
@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 跳转到主页面，且携带用户类型信息
     * @return
     */
    @RequestMapping("/show")
    public String type(){
        return null;
    }
    /**
     * 查询全部数据返回json对象
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public LayuiReturn<List<User>> listUser(Integer page, Integer limit) {
        //实现分页
        Page<User> userPage = new Page<>(page,limit);
        Page<User> page1 = userService.page(userPage);
        LayuiReturn<List<User>> layuiReturn = new LayuiReturn<>();
        layuiReturn.setData(page1.getRecords());
        //这里前端需要一个总数量我们还要查询总数
        int count = userService.count();

        layuiReturn.setCount(count);
        log.info("来到了全查数据,{}" , page1.getRecords());
        return layuiReturn;
    }


}
