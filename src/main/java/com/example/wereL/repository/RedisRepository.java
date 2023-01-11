package com.example.wereL.repository;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    private final RedisOperations<String,String> redisOperations;

    public RedisRepository(RedisOperations<String, String> redisOperations) {
        this.redisOperations = redisOperations;
    }
    @Async
    public void save(final String key,final String value){
        redisOperations.opsForValue().set(key,value);
    }
    public String getValue(final String key){
        return redisOperations.opsForValue().get(key);
    }
}
