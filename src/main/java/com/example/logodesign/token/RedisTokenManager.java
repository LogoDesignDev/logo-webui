package com.example.logodesign.token;

import com.example.logodesign.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/*
设置redis 保存token定时消除
 */
@Component
public class RedisTokenManager implements TokenManager {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*
    创建token
     */
    @Override
    public String createToken(ObjectId userId) {

        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(token, userId.toString(), 1, TimeUnit.DAYS);
        return token;
    }

    /*
    检查token是否存在
     */
    @Override
    public boolean checkToken(String token) {
        if(token != null && !("").equals(token) && redisTemplate.opsForValue().get(token) != null){
            return true;
        }
        return false;
    }


    /*
    删除token
     */
    @Override
    public void delToken(String token) {
        if(redisTemplate.opsForValue().get(token) != null){
            redisTemplate.delete(token);
        }
    }

    /*
    通过token获取用户ID
     */
    @Override
    public ObjectId getUserId(String token) {
        return new ObjectId((String)(redisTemplate.opsForValue().get(token)));
    }
}
