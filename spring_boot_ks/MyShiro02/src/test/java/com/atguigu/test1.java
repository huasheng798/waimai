package com.atguigu;

import com.atguigu.bean.User;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.UserService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/12 8:47
 * @Description:
 */
@SpringBootTest
public class test1 {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Test
    public void a(){
        User user = userService.getUserByName("user");
        System.out.println(user);
    }
    @Test
    public void b(){
        List<User> list = userMapper.selectList(null);
        for (User a:list
             ) {
            System.out.println(a);
        }
    }
}
