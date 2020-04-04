package com.example.logodesign.dao;

import com.example.logodesign.token.RedisTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LogoTemplate {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTokenManager redisTokenManager;

    /*
    创建Logo，要基于机器学习
     */
    public int createLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){ //token失效或者过期
            return 1;
        }
        return 0;
    }

    /*
    删除自己的Logo
     */
    public int delLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1;
        }
        return 0;
    }


}
