package com.example.mylogo.controller;

import com.example.mylogo.dao.LogoTemplate;
import com.example.mylogo.entity.Logo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LogoController {

    @Autowired
    LogoTemplate logoTemplate;


    /*
    获取所有已发布的Logo
    社区模块
     */
    @GetMapping("/getAllPublishedLogo")
    public Map<String, Object> getAllPublishedLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        List<Logo> allPublishedLogo = logoTemplate.getAllPublishedLogo(map);
        if(allPublishedLogo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
            res.put("allPublishedLogo", allPublishedLogo);
        }
        return res;
    }






}
