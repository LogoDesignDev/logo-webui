package com.example.logodesign.controller;

import com.example.logodesign.dao.UserTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserTemplate userTemplate;

    /*
    进入开始页面
    token验证成功 则返回code=1
    token验证失败 则返回code=0
     */
    @GetMapping("/")
    public Map<String, Object> verifyToken(HttpServletRequest request){
        String token = request.getHeader("token");
        HashMap<String, Object> res = new HashMap<>();
        if(userTemplate.tokenAvailable(token)){
           res.put("code",1);
        }else{
            res.put("code", 0);
        }
        return res;
    }

    /*
    通过邮件登录
    登录通过，返回token  并且code=0
    登录失败，返回token=""  并且code=1
     */
    @PostMapping("/loginByEmail")
    public Map<String, Object> getTokenByEmail(@RequestParam Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = userTemplate.loginByEmail(map);
        if(token == null){
            res.put("code", 0);
            res.put("token", "");
        }else{
            res.put("code", 1);
            res.put("token", token);
        }
        return res;
    }

    /*
    通过电话号码登录
   登录通过，返回token  并且code=0
   登录失败，返回token=""  并且code=1
    */
    @PostMapping("/loginByPhone")
    public Map<String, Object> getTokenByPhone(@RequestParam Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        String token = userTemplate.loginByPhone(map);
        if(token == null){
            res.put("code", 0);
            res.put("token", "");
        }else{
            res.put("code", 1);
            res.put("token", token);
        }
        return res;
    }


    /*
    登出功能，删除token 默认返回code=1
     */
    @PostMapping("/logout")
    public Map<String, Object> delToken(@RequestParam Map<String, Object> map){
        String token = (String)(map.get("token"));
        userTemplate.logout(token);
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", 1);
        return res;
    }


    /*
    注册用户
    暂时不考虑主键重复
    成功返回code=1
     */
    @PostMapping("/registerUser")
    public Map<String, Object> registerUser(@RequestParam Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", 1);
        userTemplate.registUser(map);
        return res;
    }

}
