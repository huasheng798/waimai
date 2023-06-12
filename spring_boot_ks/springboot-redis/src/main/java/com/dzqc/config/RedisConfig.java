package com.dzqc.config;

import com.dzqc.Bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author:luosheng
 * @Date:2023/3/20 11:08
 * @Description:
 */
@Component
public class RedisConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean add(String key, User user) {
        try {
            redisTemplate.opsForValue().set(key, user);
        } catch (Exception e) {
            throw new RuntimeException("查询失败");
        }
        return true;
    }

    public User getById(String key) {
        User user = (User) redisTemplate.opsForValue().get(key);
        return user;
    }
}

