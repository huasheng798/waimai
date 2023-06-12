package com.dzqc.controller;

import com.dzqc.Bean.User;
import com.dzqc.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:luosheng
 * @Date:2023/3/22 10:58
 * @Description:
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping
    public String add(User user) {
        boolean add = redisConfig.add(user.getId(), user);
        if (add == true) {
            return "存储成功";
        }
        return "存储失败了";
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable("id") String id) {
     //   User user = (User) redisTemplate.opsForValue().get(id);
        User user = redisConfig.getById(id);
        if (user == null) {
            return "查出信息为空";
        }
        return user;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        User user = (User) redisTemplate.opsForValue().get(id);
        if (user == null) {
            return "当前删除的用户为空";
        }
        Boolean delete = redisTemplate.delete(id);
        return "当前已删除" + delete;
    }


}
