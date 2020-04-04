package com.example.logodesign.dao;

import com.example.logodesign.entity.Logo;
import com.example.logodesign.entity.User;
import com.example.logodesign.token.RedisTokenManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class UserTemplate {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTokenManager redisTokenManager;



    //头像 logo 是个问题
    //注册
    public int registUser(Map<String, Object> map){
        String phone = (String) map.get("phone");
        String email = (String) map.get("email");
        Query queryPhone = Query.query(Criteria.where("phone").is(phone));
        Query queryEmail = Query.query(Criteria.where("email").is(email));
        if(mongoTemplate.findOne(queryPhone, User.class) != null){
            return 1;
        }
        if(mongoTemplate.findOne(queryEmail, User.class) != null){
            return 2;
        }
        User user = new User();
        user.setUsername((String)map.get("username"));
        user.setPassword((String)map.get("password"));
        user.setEmail(email);
        user.setLogoList(new ArrayList<ObjectId>());
        user.setPhone(phone);
        mongoTemplate.save(user,"user");
        return 0;
    }

    //修改用户信息
    public void modifyUser(){

    }




    //电话登录
    public String loginByPhone(Map<String, Object> map){
        String phone = (String)(map.get("phone"));
        String password = (String)(map.get("password"));
        Query query = Query.query(Criteria.where("phone").is(phone));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null || !password.equals(user.getPassword())){
            return null;
        }else{
            //设置token, 存在redis数据库，返回token
            return redisTokenManager.createToken(user.getUserId());
        }
    }



    //邮箱登录
    public String loginByEmail(Map<String, Object> map){
        String email = (String)map.get("email");
        String password = (String)map.get("password");
        Query query = Query.query(Criteria.where("email").is(email));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null || !password.equals(user.getPassword())){
            return null;
        }else{
            //设置token, 存在redis数据库，返回token
            return redisTokenManager.createToken(user.getUserId());
        }
    }


    //登录或者验证通过之后通过token获取用户
    public User getUser(String token){
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }


    //验证token是否有效
    public boolean tokenAvailable(String token){
        if(redisTokenManager.checkToken(token)){
            return true;
        }
        return false;
    }


    //退出时删除缓存
    public void logout(String token){
        redisTokenManager.delToken(token);
    }


    public int uploadIcon(Map<String, Object> map) throws IOException {
        MultipartFile file = (MultipartFile) map.get("file");
        String token = (String) map.get("token");
        if (!redisTokenManager.checkToken(token)){
            return 1;          //token无效或者过期
        }

        if (!file.isEmpty()) {
            // 上传文件路径
            // String path = request.getServletContext().getRealPath("/upload/");



            ObjectId id = redisTokenManager.getUserId(token);


            String path = "/usr/share/nginx/html/test/image/"; //路径， 需要改变
            String filename = id.toString() + ".jpg";
            // 上传文件名
            File filePath = new File(path, filename);
            // 判断路径是否存在，如果不存在就创建一个
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            // 将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
            return 0; //成功存图片
        } else {
            return 2; //文件为空
        }
    }


}
