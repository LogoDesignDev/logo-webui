package com.example.mylogo.controller;

import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.dao.LikesTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import com.example.mylogo.extra.RedisKeyUtils;
import com.example.mylogo.extra.LikedStatusEnum;
import com.example.mylogo.extra.UserLike;
import com.example.mylogo.extra.LikedCountDTO;


import lombok.extern.slf4j.Slf4j;
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
public class UserController implements LikesTemplate{

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
           res.put("code", 200);
        }else{
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
            res.put("code", 500);
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
    @PostMapping("/user/updateIcon")
    public Map<String, Object> uploadHeader(@RequestBody Map<String, Object> map){

        HashMap<String, Object> res = new HashMap<>();
        try {
            int status = userTemplate.uploadIcon(map);
            if(status == 1){
                res.put("code", 501); //token出现问题
            }
            else if(status == 2){
                res.put("code", 502); //文件为空
            }
            else if(status == 0){
                res.put("code", 200); //上传成功
            }
        } catch (IOException e) {
            res.put("code", 503); //服务器出现差错
            e.printStackTrace();
        }
        return res;
    }

    /*
    修改用户的密码
     */
    @PostMapping("/modifyPassword")
    public Map<String, Object> modifyUserPassword(@RequestBody Map<String, Object>map){
        HashMap<String, Object> res = new HashMap<>();

        boolean result = userTemplate.modifyPassword(map);
        if(!result){
            res.put("code", 500); //token过期
        }else{
            res.put("code", 200);
        }

        return res;
    }



    /*
    修改用户的电话
     */
    @PostMapping("/modifyUserPhone")
    public Map<String, Object> modifyUserPhone(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        boolean result = userTemplate.modifyPhone(map);
        if(!result){
            res.put("code", 500); //token过期
        }else{
            res.put("code", 200);
        }

        return res;
    }

    /*
    修改用户的邮箱
    */
    @PostMapping("/modifyUserEmail")
    public Map<String, Object> modifyUserEmail(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        boolean result = userTemplate.modifyPhone(map);
        if(!result){
            res.put("code", 500); //token过期
        }else{
            res.put("code", 200);
        }

        return res;
    }


    /*
    获取用户的所有logo
     */
    @GetMapping("/getMyLogoList")
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
    @GetMapping("/user/getUserInfo")
    public Map<String, Object> getUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        User user = userTemplate.getUser(map);
        if(user == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("userInfo", user);
        }
        return res;
    }



    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, 1);
    }

    @Override
    public void decrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            UserLike userLike = new UserLike(likedUserId, likedPostId, value);
            list.add(userLike);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }

        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }

}
