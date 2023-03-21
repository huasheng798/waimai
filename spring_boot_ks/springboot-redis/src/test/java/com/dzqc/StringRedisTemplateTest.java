package com.dzqc;

import com.dzqc.Bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * @Author:luosheng
 * @Date:2023/3/20 10:12
 * @Description:
 */
@SpringBootTest
public class StringRedisTemplateTest {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    void Test(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
      //  stringStringValueOperations.set("address2","许昌电气");
        String address2 = stringStringValueOperations.get("user:200");
        System.out.println(address2);
    }

}
