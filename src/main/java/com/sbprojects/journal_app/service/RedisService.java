package com.sbprojects.journal_app.service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {


    @Autowired
    private  ObjectMapper mapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    public <T> T get(@NonNull String key, @NonNull Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null) return null;

            return mapper.readValue(o.toString(), entityClass);
        } catch (Exception e) {
            log.error("Redis error in get for key: {}", key, e);
            return null;
        }
    }

    public void set(@NonNull String key, Object o, Long ttl){
        try {
            final String jsonValue = Objects.requireNonNull(mapper.writeValueAsString(o));
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis error in set for key: {}", key, e);
        }
    }
}
