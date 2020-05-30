package com.example.mylogo.controller;

import com.example.mylogo.dao.LogoTemplate;
import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LogoController {

    @Autowired
    LogoTemplate logoTemplate;

    @Autowired
    UserTemplate userTemplate;


    /*
    获取所有已发布的Logo
    社区模块
     */
    @GetMapping("/getAllPublishedLogo")
    public Map<String, Object> getAllPublishedLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        List<Logo> allPublishedLogo = logoTemplate.getAllPublishedLogo(map);
        if(allPublishedLogo == null){
            res.put("code", 533); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("allPublishedLogo", allPublishedLogo);
        }
        return res;
    }


    // 获取首页需要的logo
    @GetMapping("/home/getRecommend")
    public Map<String, Object> getHomePageLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        List<Logo> allPublishedLogo = logoTemplate.getAllPublishedLogo(map);
        List<Map<String, Object>> result = new ArrayList<>();
        int n = allPublishedLogo.size();
        for (int i = 0; i < n && i < 8; i++){
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("url", allPublishedLogo.get(i).getUrl());
            hm.put("desc", allPublishedLogo.get(i).getIntroduce());
            hm.put("title", allPublishedLogo.get(i).getTitle());
            result.add(hm);
        }
        if(allPublishedLogo == null || allPublishedLogo.size() == 0){
            res.put("code", 534); //表示token不存在或失效
        }else{
            res.put("code", 200); // 安装文档
            // 每个logo需要 url, title, desc三个字段
            res.put("logo", result);
        }
        return res;
    }

    // 获取首页需要的user
    @GetMapping("/home/getDesigner")
    public Map<String, Object> getHomePageUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        // TODO
        List<User> allUser = userTemplate.getAllUser(map);
        List<Map<String, Object>> result = new ArrayList<>();
        int n = allUser.size();
        for (int i = 0; i < n && i < 9; i++){
            HashMap<String, Object> hm = new HashMap<>();
            // "uid": "10000"，
            //        "userPicUrl": "http://127.0.0.1/1.png",
            //        "username": "我是设计师1",
            //        "like": 1234,
            //        "collect": 1234,
            //        "prod": 1234
            hm.put("uid", allUser.get(i).getUserId());
            hm.put("userPicUrl", allUser.get(i).getUrl());
            hm.put("username", allUser.get(i).getUsername());
            hm.put("like", allUser.get(i).getBeLikedCount()); // 获赞数量
            hm.put("collect", allUser.get(i).getMarkedLogoList().size()); // 收藏0
            hm.put("prod", allUser.get(i).getLogoList().size());
            result.add(hm);
        }
        if(allUser == null || allUser.size() == 0){
            res.put("code", 535); //表示token不存在或失效
        }else{
            res.put("code", 200); // 安装文档
            res.put("desinger", result);
        }
        return res;
    }

    @GetMapping("/home/getFirstUrl")
    public Map<String, Object> getFirstUrl(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        // TODO
        List<Logo> allLogo = logoTemplate.getAllPublishedLogo(map);
        ArrayList<String> result = new ArrayList<>();
        int n = allLogo.size();

        if(allLogo == null || allLogo.size() == 0){
            res.put("code", 536); //表示没有logo
        }else{
            result.add(allLogo.get(0).getUrl());
            if (n >= 2){
                result.add(allLogo.get(1).getUrl());
            }else{
                result.add(allLogo.get(0).getUrl());
            }
            if (n >= 3){
                result.add(allLogo.get(2).getUrl());
            }else{
                result.add(allLogo.get(0).getUrl());
            }
            res.put("code", 200); // 安装文档
            res.put("logoUrl", result);
        }
        return res;
    }


}
