package com.example.redisexample.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController2 {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/setKey")
    public void setKey(@RequestParam("key") String key, @RequestParam("value") String value){
        redisTemplate.opsForValue().set(key, value);
    }

    @GetMapping("/getValue")
    public String setKeys(@RequestParam("key") String key){
        return redisTemplate.opsForValue().get(key).toString();
    }
}
