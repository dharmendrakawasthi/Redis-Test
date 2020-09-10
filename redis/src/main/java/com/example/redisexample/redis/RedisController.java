
/*** Author - Piyush Aggarwal ***/
/*** https://www.linkedin.com/in/piyush-aggarwal-960a81100/ ***/

package com.example.redisexample.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.*;

@RestController
public class RedisController {

    @Autowired
    Jedis jedis;

    /*** Dealing with normal key value pairs ***/

    @PostMapping("/set_key")
    public String setKey(@RequestParam("key") String key, @RequestParam("value") String value){
        return jedis.set(key, value);
    }

    @PostMapping("/set_keys")
    public String setKeys(@RequestBody String[]keyValues){
        return jedis.mset(keyValues);
    }

    @GetMapping("/get_value")
    public String getValue(@RequestParam("key") String key){
        return jedis.get(key);
    }

    @GetMapping("/get_values")
    public List<String> getValues(@RequestBody String[] keys){
        return jedis.mget(keys);
    }

    @PostMapping("/set_key_ex")
    public String setKeyWithExpiry(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expiry") int time){
        return jedis.setex(key, time, value);
    }


    /*** Dealing with lists ***/

    @PostMapping("/add_to_left")
    public Long addToLeft(@RequestParam("key") String key, @RequestBody String[]values){
        return jedis.lpush(key, values);
    }

    @PostMapping("/add_to_right")
    public Long addToRight(@RequestParam("key") String key, @RequestBody String[]values){
        return jedis.rpush(key, values);
    }

    @PostMapping("/remove_from_left")
    public String removeFromLeft(@RequestParam("key") String key){
        return jedis.lpop(key);
    }

    @PostMapping("/remove_from_right")
    public String removeFromRight(@RequestParam("key") String key){
        return jedis.rpop(key);
    }


    /*** Dealing with hashes ***/

    @PostMapping("/set_hash_keys")
    public String setHashFields(@RequestParam("key") String key, @RequestBody Map<String, String> fieldMap){
        return jedis.hmset(key, fieldMap);
    }

    @GetMapping("/get_all_keys")
    public Set<String> getKeys(){
        return jedis.keys("*");
    }

    @PostMapping("/pipeline")
    public void pipeline(){
        Pipeline pipeline = jedis.pipelined();
        Response<Set<String>>responses = pipeline.keys("*");

        for(int i=0;i<10000;i++){
            pipeline.setex("k:" + i, 1000, "v:" + i);
        }


//        Set<String> keys = responses.get();
//        for(String key : keys){
//            pipeline.del(key);
//        }

        pipeline.sync();
    }

    @PostMapping("/wt_pipeline")
    public void withoutPipeline(){
        Set<String>keys = jedis.keys("p*");
        for(int i=0;i<10000;i++){
            jedis.setex("k:" + i, 1000, "v:" + i);
        }
    }

    @PostMapping("/transaction")
    public void transaction(){

        jedis.multi();
    }

}
