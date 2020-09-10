package com.example.redisexample.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSocketFactory;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("redis-12397.c240.us-east-1-3.ec2.cloud.redislabs.com");
        redisConfig.setPort(12397);
        redisConfig.setPassword("h78Nf1x838EVbFSgFzZc7OmyZ9hSwxRq");
//        JedisClientConfiguration jedisConfig;
//        jedisConfig = JedisClientConfiguration
//                    .builder()
//                    .useSsl()
//                    .build();
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisConfig);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    Jedis jedis(){
//        Jedis jedis = new Jedis("redis-12397.c240.us-east-1-3.ec2.cloud.redislabs.com", 12397);
//        jedis.auth("h78Nf1x838EVbFSgFzZc7OmyZ9hSwxRq");
        return new Jedis();
    }

}
