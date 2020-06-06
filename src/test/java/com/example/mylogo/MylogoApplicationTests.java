package com.example.mylogo;

import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MylogoApplicationTests {

    @Autowired
    UserTemplate userTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() throws IOException {
//        Map<String, Object> map = new HashMap<>();
//        map.put("username", "lmy");
//        map.put("password", "123");
//        map.put("email", "em");
//        map.put("phone", "ph");
//        String token = "257fcf343f6344e1963c79e18ea18abf";
//        String newPwd = "123456";
//        String oldPwd = "123";
//        map.put("token", token);
//        map.put("oldPwd", oldPwd);
//        map.put("newPwd", newPwd);
//        map.put("keyword", "lm");
//        map.put("order", 1);
//        List<User> list = userTemplate.findUserByKeyword(map);
//        for(User user:list){
//            System.out.println(user.getUserId().getTimestamp() + " " + user.getUsername());
//        }
//        userTemplate.modifyPassword(map);
//        userTemplate.uploadIcon(map);

        //userTemplate.registUser(map);

//        User user = new User();
//        user.setUsername("lmb");
//        user.setPassword("123");
//        System.out.println("之前 " + user.getUserId());
//        mongoTemplate.save(user);
//        System.out.println("之后 " + user.getUserId());
//        Query query = Query.query(Criteria.where("username").is("lmb"));
//
//        User user1 = mongoTemplate.findOne(query, User.class);
//        assert user1 != null;
//        System.out.println("查找 " + user1.getUserId().toString());
//
//
//        String res = userTemplate.login(map);
//        System.out.println(res);

    }

}
