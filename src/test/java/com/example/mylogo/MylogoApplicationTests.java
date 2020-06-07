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
//        for(int i = 1;i < 100;i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("username", "username"+i);
//            map.put("password", "password"+i);
//            map.put("email", "email"+i);
//            map.put("phone", "phone"+i);
//
//            userTemplate.registUser(map);
//        }

//        List<User> list = userTemplate.findUserByKeyword(map);
//        for(User user:list){
//            System.out.println(user.getUserId().toString() + " " + user.getUsername());
//        }
//        userTemplate.modifyPassword(map);
//        userTemplate.uploadIcon(map);



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
