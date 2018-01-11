package com.arraypay.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by fred on 2017/12/5.
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long expiredTime) {
        set(key, value, expiredTime, TimeUnit.DAYS);
    }

    public void set(String key, String value, long expiredTime, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, expiredTime, timeUnit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
