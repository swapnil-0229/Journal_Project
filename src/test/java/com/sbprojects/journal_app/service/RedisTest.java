package com.sbprojects.journal_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void emailTest() {
        redisTemplate.opsForValue().set("email", "lalala@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        int a = 1;
    }
}
