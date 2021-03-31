package com.gg_pigs._config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
public class RedisConfigurer {

    @Value("${application.redis.host}")
    private String host;

    @Value("${application.redis.port}")
    private int port;

    private String connectionFactoryType = "lettuce";

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Lettuce Connection Factory
        if(connectionFactoryType.equalsIgnoreCase("lettuce")) {
            return new LettuceConnectionFactory(host, port);
        }

        // Jedis Connection Factory
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        return redisTemplate;
    }
}
