package com.example.logodesign;

import com.example.logodesign.dao.UserTemplate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;

@SpringBootTest
class LogodesignApplicationTests {

    @Autowired
    UserTemplate userTemplate;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", "1150050235");
        map.put("password", "abc");
        //userTemplate.registUser(map);
        //System.out.println(userTemplate.loginByEmail(map));
        System.out.println(userTemplate.tokenAvailable("756de9ca72a4416f878951aa91e5ba1b"));

    }



}
