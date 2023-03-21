package com.mybatis;

import com.mybatis.bean.User;
import com.mybatis.mapper.UserMapper;
import com.mybatis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootMybatisPlusDemoApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        List<User> users = userService.list();
        for (User u:users
             ) {
            System.out.println(u);
        }
    }

}
