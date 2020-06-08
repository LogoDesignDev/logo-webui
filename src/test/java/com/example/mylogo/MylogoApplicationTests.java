package com.example.mylogo;

import com.example.mylogo.dao.LogoTemplate;
import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.User;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.*;
import java.net.*;
import java.net.http.HttpRequest;
import java.util.*;

@SpringBootTest
class MylogoApplicationTests {

    @Autowired
    UserTemplate userTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LogoTemplate logoTemplate;

    @Test
    @Ignore
    void addManyUser() throws IOException {
//        for(int i = 1;i <= 10;i++){
//            Map<String, Object> map = new HashMap<>();
//            int num = i * 6667;
//            map.put("username", "username"+ num);
//            map.put("password", "password"+ num);
//            map.put("email", "email"+ num);
//            map.put("phone", "phone"+ num);
//
//            int result = userTemplate.registUser(map);
//
//            try{
//                Thread.sleep(1000);
//            }catch(InterruptedException e){
//                System.out.println("Err during sleeping...");
//                Assert.fail();
//            }
//        }
//
//        // check
//        ArrayList<User> all = new ArrayList<>();
//        all.addAll(userTemplate.getAllUser(new HashMap<>()));
//
//        int n = all.size();
//        for (int i = 0 ; i < n; i++){
//            System.out.println(all.get(i).getUsername());
//        }

//        List<User> list = userTemplate.findUserByKeyword(map);
//        for(User user:list){
//            System.out.println(user.getUserId().toString() + " " + user.getUsername());
//        }
//        userTemplate.modifyPassword(map);
//        userTemplate.uploadIcon(map);
//
//
//
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
