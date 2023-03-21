package com.dzqz;

import com.dzqz.bean.User;
import com.dzqz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyShiroApplicationTests {
    @Autowired
    private UserService service;

    @Test
    void contextLoads() {
        List<User> list = service.list(null);
        for (User a:list
             ) {
            System.out.println(list);
        }
    }

}
