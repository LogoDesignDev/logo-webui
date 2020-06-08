package com.example.mylogo.controller;

import com.example.mylogo.dao.LogoTemplate;
import com.example.mylogo.dao.UserTemplate;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

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
    public Map<String, Object> getAllPublishedLogo(Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();
        /*
        String token = (String)map.get("token");
        if (token == null || userTemplate.tokenAvailable(token)){
            res.put("code", 500);
            return res;
        }*/

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
    @GetMapping("/home/getRecommend")
    public Map<String, Object> getHomePageLogo(Map<String, Object> map){
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
            res.put("code", 501); //表示token不存在或失效
        }else{
            res.put("code", 200); // 安装文档
            // 每个logo需要 url, title, desc三个字段
            res.put("logo", result);
        }
        return res;
    }

    // 获取首页需要的user
    @GetMapping("/home/getDesigner")
    public Map<String, Object> getHomePageUser(Map<String, Object> map){
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
                hm.put("userPicUrl", allUser.get(i).getUserPicUrl());
                hm.put("username", allUser.get(i).getUsername());
                hm.put("like", allUser.get(i).getBeLikedCount()); // 获赞数量
                hm.put("collect", allUser.get(i).getMarkedLogoList().size()); // 收藏0
                hm.put("prod", allUser.get(i).getLogoList().size());
                result.add(hm);
        }
        if(allUser == null || allUser.size() == 0){
            res.put("code", 501);
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

        if (count > 8 || paramList == null || count != paramList.size()){
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

    @GetMapping("/home/getFirstUrl")
    public Map<String, Object> getFirstUrl(Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

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
            res.put("author",logo.getAuthorName());
            res.put("name",logo.getName());
        }
        return res;
    }

    /*
    用户添加一个logo
    */
    @PostMapping("/mylogo/galleryid/addlogo")
    public Map<String, Object> addNewLogo(@RequestBody Map<String, Object> map){
        HashMap<String,Object> res = logoTemplate.addLogo(map);
        if(res == null){
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
        }
        return res;
    }

    /*
    用户取消收藏一个logo
    */
    @PostMapping("/mylogo/uncollect")
    public Map<String, Object> delcollectLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Logo logo = logoTemplate.delcollectLogo(map);
        if(logo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
        }
        return res;
    }

    /*
   用户取消点赞一个logo
   */
    @PostMapping("/mylogo/unlike")
    public Map<String, Object> dellikeLogo(@RequestBody Map<String, Object> map){
        HashMap<String, Object> res = new HashMap<>();

        Logo logo = logoTemplate.dellikeLogo(map);
        if(logo == null){
            res.put("code", 500); //表示token不存在或失效
        }else{
            res.put("code", 200);
        }
        return res;
    }

    @PostMapping("/upload")
    public Map<String, Object> imageUpload(@RequestParam("file") MultipartFile file)
    {
        HashMap<String, Object> res = new HashMap<>();
        String url = logoTemplate.fileUpload(file);
        if(url == null){
            res.put("code",510);
        }else{
            res.put("code",200);
            res.put("url",url);
        }
        return res;
    }

    @GetMapping("/genLogoForAllUser")
    public Map<String, Object> genLogos(Map<String, Object> amap) throws Exception{
        HashMap<String, Object> res = new HashMap<>();
        String text = "hello~";
        // init
        ArrayList<User> all = new ArrayList<>();
        all.addAll(userTemplate.getAllUser(new HashMap<>()));
        System.out.println("Get all users, len = " + all.size());

        int n = all.size();
        for (int i = 0 ; i < n; i++){
            HashMap<String, Object> map = new HashMap<>();
            String bs64 = logoTemplate.createLogo(map);

            String username = all.get(i).getUsername();
            String pwd = all.get(i).getPassword();
            map.put("username", username);
            map.put("password", pwd);

            // 登录
            String token = userTemplate.login(map);
            if (token == null){
                System.out.println("invalided token.. for username = " + username);
                res.put("code", 500);
                return res;
            }
            map.put("token", token);

            // 创建gallery test
            map.put("name", "test");
            map.put("region", "私有");
            logoTemplate.addGallery(map);

            // add logo
            map.put("base64", bs64);
            int resp = logoTemplate.saveLogo(map);
            if (resp != 0){
                System.out.println("Failed to save logo for resp = " + resp);
                res.put("code", 500);
                return res;
            }
            // logout
            userTemplate.logout(token);
            System.out.println("user " + username + " done..");

            Thread.sleep(500); // 500ms
        }
        res.put("code", 200);
        return res;
    }


}
