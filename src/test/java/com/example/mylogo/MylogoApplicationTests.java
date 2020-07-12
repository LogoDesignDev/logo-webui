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
//        for(int i = 1;i <= 100;i++){
//            Map<String, Object> map = new HashMap<>();
//            map.put("username", "username"+i);
//            map.put("password", "password"+i);
//            map.put("email", "email"+i);
//            map.put("phone", "phone"+i);
//            userTemplate.registUser(map);
//        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword" ,"name");
        map.put("order", 3);
        List<User> list = userTemplate.findUserByKeyword(map);
        System.out.println(list.size());
        for(int i = 0;i < list.size();i++){
            User user = list.get(i);
            System.out.println(user.getUsername() + " " + user.getUserId() + " " + user.getFansCount() + " " + user.getBeLikedCount() + " " + user.getBeMarkedCount());

        }

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


    }
}
