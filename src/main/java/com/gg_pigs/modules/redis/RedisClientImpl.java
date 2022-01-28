package com.gg_pigs.modules.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisClientImpl implements RedisClient {

    @Autowired StringRedisTemplate stringRedisTemplate;

    @Override
    public String getStringValue(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        return valueOperations.get(key);
    }

    @Override
    public boolean storeStringValue(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);

        return true;
    }

    @Override
    public boolean storeStringValueForSeconds(String key, String value, int timeout) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);

        return true;
    }
}
