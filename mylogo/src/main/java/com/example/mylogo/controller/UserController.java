package com.example.mylogo.controller;

import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserTemplate userTemplate;

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
    通过邮件登录
    登录通过，返回token  并且code=200
    登录失败，返回token=""  并且code=500
     */
    @PostMapping("/loginByEmail")
    public Map<String, Object> getTokenByEmail(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = userTemplate.loginByEmail(map);
        if(token == null){
            res.put("code", 500);
            res.put("token", "");
        }else{
            res.put("code", 200);
            res.put("token", token);
        }
        return res;
    }

    /*
    通过电话号码登录
   登录通过，返回token  并且code=2003
   登录失败，返回token=""  并且code=5003
    */
    @PostMapping("/loginByPhone")
    public Map<String, Object> getTokenByPhone(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = userTemplate.loginByPhone(map);
        if(token == null){
            res.put("code", 500);
            res.put("token", "");
        }else{
            res.put("code", 200);
            res.put("token", token);
        }
        return res;
    }


    /*
    登出功能，删除token 默认返回code=200
     */
    @PostMapping("/logout")
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
    电话号码重复返回 code=5001
    邮箱重复返回 code=5002
    成功返回code=200
     */
    @PostMapping("/registerUser")
    public Map<String, Object> registerUser(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        int status = userTemplate.registUser(map);
        if(status == 1){
            res.put("code", 5001); //电话号码重复
        }
        else if(status == 2){
            res.put("code", 5002); //邮箱重复
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
    @PostMapping("/updateIcon")
    public Map<String, Object> uploadHeader(@RequestBody Map<String, Object> map){

        HashMap<String, Object> res = new HashMap<>();
        try {
            int status = userTemplate.uploadIcon(map);
            if(status == 1){
                res.put("code", 5001); //token出现问题
            }
            else if(status == 2){
                res.put("code", 5002); //文件为空
            }
            else if(status == 0){
                res.put("code", 200); //上传成功
            }
        } catch (IOException e) {
            res.put("code", 5003); //服务器出现差错
            e.printStackTrace();
        }
        return res;
    }


    /*
    修改用户的用户名
     */
    @PostMapping("/modifyUserName")
    public Map<String, Object> modifyUserName(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();


        return res;
    }

    /*
    修改用户的电话
     */
    @PostMapping("/modifyUserPhone")
    public Map<String, Object> modifyUserPhone(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();


        return res;
    }

    /*
    修改用户的邮箱
     */
    @PostMapping("/modifyUserEmail")
    public Map<String, Object> modifyUserEmail(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();


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
    @GetMapping("/getUser")
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


}
