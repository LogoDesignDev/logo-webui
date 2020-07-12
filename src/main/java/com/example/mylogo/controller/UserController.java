package com.example.mylogo.controller;

import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;


import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.Cursor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RestController
public class UserController{

    @Autowired
    UserTemplate userTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    /*
    进入开始页面
    token验证成功 则返回code=200
    token验证失败 则返回code=500
     */
    @GetMapping("/")
    public Map<String, Object> verifyToken(HttpServletRequest request){
        String token = request.getHeader("token");
        HashMap<String, Object> res = new HashMap<>();
        if(userTemplate.tokenAvailable(token)){
           res.put("code", 200);     }else{
            res.put("code", 500);
        }
        return res;
    }

    /*
    通过用户名登录
    登录通过，返回token  并且code=200
    登录失败，返回token=""  并且code=500
     */
    @PostMapping("/user/login")
    public Map<String, Object> getTokenByEmail(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = userTemplate.login(map);
        if(token == null){
            res.put("code", 501); //
        }else{
            res.put("code", 200);
            res.put("token", token);
        }
        return res;
    }


    /*
    登出功能，删除token 默认返回code=200
     */
    @PostMapping("/user/logout")
    public Map<String, Object> delToken(@RequestBody Map<String, Object> map){
        String token = (String)(map.get("token"));
        userTemplate.logout(token);
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", 200);
        return res;
    }


    /*
    注册用户
    考虑邮箱和电话号码是否重复
    电话号码重复返回 code=501
    邮箱重复返回 code=502
    用户名重复返回 code=503
    成功返回code=200
     */
    @PostMapping("/user/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        int status = userTemplate.registUser(map);
        if(status == 1){
            res.put("code", 501); //电话号码重复
        }
        else if(status == 2){
            res.put("code", 502); //邮箱重复
        }
        else if(status == 3){
            res.put("code", 503); //用户名重复
        }
        else{
            res.put("code", 200); //成功返回
        }
        return res;
    }

    /*
    上传头像
    成功返回code=200
    token出现问题 返回code=5001
    文件为空 返回code=5002
    服务器出现异常 返回code=5003
     */
    @PostMapping("/user/uploadUserPic")
    public Map<String, Object> uploadHeader(@RequestBody Map<String, Object> map){

        HashMap<String, Object> res = new HashMap<>();
        try {
            int status = userTemplate.uploadIcon(map);
            if(status == 1){
                res.put("code", 500); //token出现问题
            }
            else if(status == 2){
                res.put("code", 501); //base64失效
            }
            else if(status == 0){
                res.put("code", 200); //上传成功
            }
        } catch (IOException e) {
            res.put("code", 502); //服务器出现差错
            e.printStackTrace();
        }
        return res;
    }

    /*
    修改用户的密码
     */
    @PostMapping("/user/changePassword")
    public Map<String, Object> modifyUserPassword(@RequestBody Map<String, Object>map){
        HashMap<String, Object> res = new HashMap<>();

        int result = userTemplate.modifyPassword(map);
        if(result == 1){
            res.put("code", 500); //token过期
        }
        else if(result == 2){
            res.put("code", 501); //user不存在
        }
        else if(result == 3){
            res.put("code", 502); //旧密码不对
        }
        else if(result == 0){
            res.put("code", 200); // 更改密码成功
        }

        return res;
    }



    /*
    修改用户的电话
     */
    @PostMapping("/user/changePhone")
    public Map<String, Object> modifyUserPhone(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        int result = userTemplate.modifyPhone(map);
        if(result == 1){
            res.put("code", 500); //token过期
        }
        else if(result == 2){
            res.put("code", 501); //用户不存在
        }
        else if(result == 0){
            res.put("code", 200); //成功修改
        }

        return res;
    }

    /*
    修改用户的邮箱
    */
    @PostMapping("/user/changeEmail")
    public Map<String, Object> modifyUserEmail(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        int result = userTemplate.modifyEmail(map);
        if(result == 1){
            res.put("code", 500); //token过期
        }
        else if(result == 2){
            res.put("code", 501); //用户不存在
        }
        else if(result == 0){
            res.put("code", 200); //更改成功
        }

        return res;
    }


    /*
    获取用户的所有logo
     */
    @PostMapping("/getMyLogoList")
    public Map<String, Object> getMyLogoList(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        List<Logo> myLogoList = userTemplate.getMyLogoList(map);
        if(myLogoList == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("myLogoList", myLogoList);
        }
        return res;
    }


    /*
    获取用户信息
     */
    @PostMapping("/user/getUserInfo")
    public Map<String, Object> getUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        User user = userTemplate.getUser(map);
        if(user == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("uid", user.getUserId().toString());
            res.put("username", user.getUsername());
            res.put("email", user.getEmail());
            res.put("phone", user.getPhone());
            res.put("userPicUrl", user.getUserPicUrl());
        }
        return res;
    }

    /*
    获取用户关注列表
     */
    @PostMapping("/personal/getFollowList")
    public Map<String, Object> getFocus(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        ArrayList<Map> list = userTemplate.getFocusList(map);
        if(list == null){
            res.put("followList", list); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("followList",list);
        }
        return res;
    }

    /*
        获取用户粉丝列表
         */
    @PostMapping("/personal/getFansList")
    public Map<String, Object> getFans(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        ArrayList<Map> list = userTemplate.getFansList(map);
        if(list == null){
            res.put("fansList", list); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("fansList",list);
        }
        return res;
    }

    /*
        关注用户
         */
    @PostMapping("/personal/follow")
    public Map<String, Object> followUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        String flag = userTemplate.follow(map);
        if(flag == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("uid",flag);
        }
        return res;
    }

    /*
        取消关注用户
         */
    @PostMapping("/personal/unfollow")
    public Map<String, Object> unfollowUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        String flag = userTemplate.unfollow(map);
        if(flag == null){
            res.put("code", 500); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("uid",flag);
        }
        return res;
    }

    /*
        用户作品数
         */
    @PostMapping("/personal/getProdCount")
    public Map<String, Object> prodCount(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Integer count = userTemplate.prodCount(map);
        if(count == -1){
            res.put("code", 520); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("count",count);
            res.put("uid",map.get("uid"));
        }
        return res;
    }

    /*
        用户粉丝数
         */
    @PostMapping("/personal/getFansCount")
    public Map<String, Object> fansCount(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Integer count = userTemplate.fansCount(map);
        if(count == -1){
            res.put("code", 520); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("count",count);
            res.put("uid",map.get("uid"));
        }
        return res;
    }

    /*
        用户关注数
         */
    @PostMapping("/personal/getFollowCount")
    public Map<String, Object> focusCount(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Integer count = userTemplate.focusCount(map);
        if(count == -1){
            res.put("code", 520); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("count",count);
            res.put("uid",map.get("uid"));
        }
        return res;
    }

    /*
        用户被赞数
         */
    @PostMapping("/personal/getUserLikeCount")
    public Map<String, Object> belikedCount(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Integer count = userTemplate.belikedCount(map);
        if(count == -1){
            res.put("code", 520); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("count",count);
            res.put("uid",map.get("uid"));
        }
        return res;
    }

    /*
        用户被收藏数
         */
    @PostMapping("/personal/getUserCollectCount")
    public Map<String, Object> beMarkedCount(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Integer count = userTemplate.beMarkedCount(map);
        if(count == -1){
            res.put("code", 520); //表示查询不到
        }else{
            res.put("code", 200);
            res.put("count",count);
            res.put("uid",map.get("uid"));
        }
        return res;
    }


    // 通过关键字查找用户
    @PostMapping("/search/author")
    public Map<String, Object> findUserByKeyword(@RequestBody Map<String, Object>map){
        HashMap<String, Object> res = new HashMap<>();
        List<User> authorList = userTemplate.findUserByKeyword(map);
        res.put("authorList", authorList);
        res.put("code", 200);
        return res;
    }

    @GetMapping("/addfocus")
    public Map<String, Object> addUserFocus(){
        ArrayList<User> userArrayList = new ArrayList<>();
        ArrayList<String> tokenArrayList = new ArrayList<>();
        ArrayList<String> userIdArrayList = new ArrayList<>();
        userArrayList.addAll(userTemplate.getAllUser(new HashMap<>()));

        for (int i = 0; i < userArrayList.size(); i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("username",userArrayList.get(i).getUsername());
            map.put("password",userArrayList.get(i).getPassword());
            userIdArrayList.add(userArrayList.get(i).getuId());
            tokenArrayList.add(userTemplate.login(map));
        }

        for (int i = 0; i < tokenArrayList.size()-3; i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",tokenArrayList.get(i));
            for (int j = i+1; j < i+4; j++) {
                map.put("uid",userIdArrayList.get(j));
                userTemplate.follow(map);
            }
        }

        for (int i = tokenArrayList.size()-3; i < tokenArrayList.size(); i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("token",tokenArrayList.get(i));
            for (int j = 0; j < 3; j++) {
                map.put("uid",userIdArrayList.get(j));
                userTemplate.follow(map);
            }
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("code",200);
        return map;
    }

    @GetMapping("/addMangpeople")
    public Map<String, Object> addPeople(){
        for(int i = 1;i <= 100;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("username", "username"+i);
            map.put("password", "password"+i);
            map.put("email", "email"+i);
            map.put("phone", "phone"+i);
            userTemplate.registUser(map);
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("code",200);
        return map;
    }

}
