package com.example.logodesign.dao;

import com.example.logodesign.entity.Logo;
import com.example.logodesign.entity.User;
import com.example.logodesign.token.RedisTokenManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class UserTemplate {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTokenManager redisTokenManager;



    //头像 logo 是个问题
    //注册
    public void registUser(Map<String, Object> map){
        User user = new User();
        user.setUsername((String)map.get("username"));
        user.setPassword((String)map.get("password"));
        user.setEmail((String)map.get("email"));
        user.setLogoList(new ArrayList<ObjectId>());
        user.setPhone((String)map.get("phone"));
        mongoTemplate.save(user,"user");
    }

    //修改用户信息
    public void modifyUser(){

    }




    //电话登录
    public String loginByPhone(Map<String, Object> map){
        String phone = (String)(map.get("phone"));
        String password = (String)(map.get("password"));
        Query query = Query.query(Criteria.where("phone").is(phone));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null || !password.equals(user.getPassword())){
            return null;
        }else{
            //设置token, 存在redis数据库，返回token
            return redisTokenManager.createToken(user.getUserId());
        }
    }



    //邮箱登录
    public String loginByEmail(Map<String, Object> map){
        String email = (String)map.get("email");
        String password = (String)map.get("password");
        Query query = Query.query(Criteria.where("email").is(email));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null || !password.equals(user.getPassword())){
            return null;
        }else{
            //设置token, 存在redis数据库，返回token
            return redisTokenManager.createToken(user.getUserId());
        }
    }


    //登录或者验证通过之后通过token获取用户
    public User getUser(String token){
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }


    //验证token是否有效
    public boolean tokenAvailable(String token){
        if(redisTokenManager.checkToken(token)){
            return true;
        }
        return false;
    }


    //退出时删除缓存
    public void logout(String token){
        redisTokenManager.delToken(token);
    }


}
