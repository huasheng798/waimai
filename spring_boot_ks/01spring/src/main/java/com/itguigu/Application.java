package com.itguigu;

import com.itguigu.bean.Teacher;
import com.itguigu.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        User user = run.getBean(User.class);
        System.out.println(user);
    }

}
