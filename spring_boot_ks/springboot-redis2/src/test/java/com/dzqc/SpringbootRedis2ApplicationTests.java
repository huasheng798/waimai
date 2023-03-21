package com.dzqc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootRedis2ApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name","zz");

        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
