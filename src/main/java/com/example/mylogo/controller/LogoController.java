package com.example.mylogo.controller;

import com.example.mylogo.dao.LogoTemplate;
import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import com.example.mylogo.entity.Gallery;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/getAllPublishedLogo")
    public Map<String, Object> getAllPublishedLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = (String)map.get("token");
        if (token == null || userTemplate.tokenAvailable(token)){
            res.put("code", 500);
            return res;
        }

        List<Logo> allPublishedLogo = logoTemplate.getAllPublishedLogo(map);
        if(allPublishedLogo == null){
            res.put("code", 501); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("allPublishedLogo", allPublishedLogo);
        }
        return res;
    }


    // 获取首页需要的logo
    @PostMapping("/home/getRecommend")
    public Map<String, Object> getHomePageLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = (String)map.get("token");
        if (token == null || userTemplate.tokenAvailable(token)){
            res.put("code", 500);
            return res;
        }

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
            res.put("code", 501); //表示token不存在或失效
        }else{
            res.put("code", 200); // 安装文档
            // 每个logo需要 url, title, desc三个字段
            res.put("logo", result);
        }
        return res;
    }

    // 获取首页需要的user
    @PostMapping("/home/getDesigner")
    public Map<String, Object> getHomePageUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = (String)map.get("token");
        if (token == null || userTemplate.tokenAvailable(token)){
            res.put("code", 500);
            return res;
        }

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
            hm.put("userPicUrl", allUser.get(i).getUserPicUrl());
            hm.put("username", allUser.get(i).getUsername());
            hm.put("like", allUser.get(i).getBeLikedCount()); // 获赞数量
            hm.put("collect", allUser.get(i).getMarkedLogoList().size()); // 收藏0
            hm.put("prod", allUser.get(i).getLogoList().size());
            result.add(hm);
        }
        if(allUser == null || allUser.size() == 0){
            res.put("code", 501); //表示token不存在或失效
        }else{
            res.put("code", 200); // 安装文档
            res.put("desinger", result);
        }
        return res;
    }

    // TODO
    @PostMapping("/creation/generate")
    public Map<String, Object> createLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        Integer count = (Integer)map.get("count");
        ArrayList<Map<String, Object>> paramList = (ArrayList<Map<String, Object>>)map.get("paramsList");

        if (count > 8 || count != paramList.size()){
            res.put("code", 501); // invalid args.
            return res;
        }
        // for each map, create a logo
        //      save logo to db.
        ArrayList<String> bss = new ArrayList<>();
        for (int i = 0; i < count; i++){
            String bs64 = this.logoTemplate.createLogo(paramList.get(i));
            bss.add(bs64);
        }
        res.put("base64List", bss);
        res.put("code", 200);
        return res;
    }

    @PostMapping("/home/getFirstUrl")
    public Map<String, Object> getFirstUrl(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = (String)map.get("token");
        if (token == null || userTemplate.tokenAvailable(token)){
            res.put("code", 500);
            return res;
        }

        List<Logo> allLogo = logoTemplate.getAllPublishedLogo(map);
        ArrayList<String> result = new ArrayList<>();
        int n = allLogo.size();

        if(allLogo == null || allLogo.size() == 0){
            res.put("code", 501); //表示没有logo
        }else{
            String firstEle = allLogo.get(0).getUrl();
            result.add(firstEle);
            if (n >= 2){
                result.add(allLogo.get(1).getUrl());
            }else{
                result.add(firstEle);
            }
            if (n >= 3){
                result.add(allLogo.get(2).getUrl());
            }else{
                result.add(firstEle);
            }
            res.put("code", 200); // 安装文档
            res.put("logoUrl", result);
        }
        return res;
    }

    /*
    获取用户全部logo图库
    */
    @PostMapping("/mylogo")
    public Map<String, Object> getAllGallery(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        ArrayList<Map> allGallery = logoTemplate.getAllGallery(map);
        if(allGallery == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("logoList",allGallery);
        }
        return res;
    }

    /*
    获取用户logo图库详情
    */
    @PostMapping("/mylogo/id")
    public Map<String, Object> getAllGalleryInfo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        ArrayList<Map> allLogo = logoTemplate.getAllLogo(map);
        if(allLogo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("items",allLogo);
        }
        return res;
    }

    /*
    用户添加logo图库
    */
    @PostMapping("/mylogo/addgallery")
    public Map<String, Object> addNewGallery(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        String createtime = logoTemplate.addGallery(map);
        if(createtime == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("createtime",createtime);
        }
        return res;
    }

    /*
    用户修改logo图库信息
    */
    @PostMapping("/mylogo/updategallery")
    public Map<String, Object> updateGallery(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        String updatetime = logoTemplate.updateGallery(map);
        if(updatetime == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("updatetime",updatetime);
        }
        return res;
    }

    /*
    用户删除logo图库
    */
    @PostMapping("/mylogo/deletegallery")
    public Map<String, Object> delGallery(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        int flag = logoTemplate.delGallery(map);
        if(flag == 0){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
        }
        return res;
    }

    /*
    获取单个logo详情
    */
    @PostMapping("/mylogo/galleryid/logoid")
    public Map<String, Object> getLogoInfo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Logo logo = logoTemplate.getLogoInfo(map);
        if(logo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("collect",logo.getCollect());
            res.put("like",logo.getLike());
            res.put("author",logo.getAuthorId());
            res.put("name",logo.getName());
        }
        return res;
    }

    /*
    用户添加一个logo
    */
    @PostMapping("/mylogo/galleryid/addlogo")
    public Map<String, Object> addNewLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        int flag = logoTemplate.addLogo(map);
        if(flag == 0){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
        }
        return res;
    }

    /*
    用户删除一个logo
    */
    @PostMapping("/mylogo/deletelogo")
    public Map<String, Object> delLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        int flag = logoTemplate.delGalleryLogo(map);
        if(flag == 0){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
        }
        return res;
    }

    /*
    用户收藏一个logo
    */
    @PostMapping("/mylogo/collect")
    public Map<String, Object> collectLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Logo logo = logoTemplate.collectLogo(map);
        if(logo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("galleryid",map.get("galleryid"));
            res.put("logoid",logo.getLogoId().toString());
            res.put("like",logo.getCollect());
        }
        return res;
    }

    /*
   用户点赞一个logo
   */
    @PostMapping("/mylogo/like")
    public Map<String, Object> likeLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Logo logo = logoTemplate.likeLogo(map);
        if(logo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("galleryid",map.get("galleryid"));
            res.put("logoid",logo.getLogoId().toString());
            res.put("like",logo.getLike());
        }
        return res;
    }

}
