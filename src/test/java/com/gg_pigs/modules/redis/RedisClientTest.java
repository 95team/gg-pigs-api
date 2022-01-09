package com.gg_pigs.modules.redis;

import com.gg_pigs.modules.redis.config.RedisConfigurer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest(
        classes = {
                RedisConfigurer.class,
                RedisClient.class,
                RedisClientImpl.class,
        }
)
class RedisClientTest {

    @Autowired StringRedisTemplate redisTemplate;
    @Autowired RedisClient redisClient;

    @Test
    public void Test_connection() {
        // Given
        String key = "test:connection";
        String value = "Hello, Redis!";
        int timeout = 5;

        // When
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);

        // Then
        Assertions.assertThat(valueOperations.get(key)).isEqualTo(value);
    }

    @Test
    public void Test_getStringValue() {
        // [INFO]
        // 1. 해당 Key - Value 값은 사전에 Redis 에 존재해야 테스트가 성공합니다.

        // Given
        String key = "test:string:permanent";
        String value = "Hello, Redis!";

        // When
        String result = redisClient.getStringValue(key);

        // Then
        Assertions.assertThat(result).isEqualTo(value);
    }

    @Test
    public void Test_storeStringValue() {
        // Given
        String key = "test:string:permanent";
        String value = "Hello, Redis!";

        // When
        boolean result = redisClient.storeStringValue(key, value);

        // Then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(redisClient.getStringValue(key)).isEqualTo(value);
    }

    @Test
    public void Test_storeStringValueForSeconds() {
        // Given
        String key = "test:string:seconds";
        String value = "Hello, Redis!";
        int timeout = 5;

        // When
        boolean result = redisClient.storeStringValueForSeconds(key, value, timeout);

        // Then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(redisClient.getStringValue(key)).isEqualTo(value);
    }
}