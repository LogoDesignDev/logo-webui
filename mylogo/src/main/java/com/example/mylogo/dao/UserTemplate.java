package com.example.mylogo.dao;

import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import com.example.mylogo.token.RedisTokenManager;
import io.netty.handler.codec.base64.Base64Decoder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Component
public class UserTemplate {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTokenManager redisTokenManager;



    //头像  是个问题
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



    /*
    邮箱登录
     */
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




    /*
    登录或者验证通过之后通过token获取用户
     */
    public User getUser(Map<String, Object> map){
        String token = (String) map.get("token");
        if (!redisTokenManager.checkToken(token)){
            return null;          //token无效或者过期
        }
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


    /*
    更新用户的头像
    图片的Base64位编码进行解码
     */
    public int uploadIcon(Map<String, Object> map) throws IOException {
        String token = (String) map.get("token");
        if (!redisTokenManager.checkToken(token)){
            return 1;          //token无效或者过期
        }
        String base64 = (String) map.get("base64");

        if (base64 != null && !"".equals(base64)) {
            ObjectId id = redisTokenManager.getUserId(token);
            String path = "/usr/share/nginx/html/icon/"; //路径， 需要改变  /icon头像

            String imgname = id.toString() + ".jpg";
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] b = decoder.decode(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path + imgname);
            out.write(b);
            out.flush();
            out.close();
            return 0; //成功存图片
        } else {
            return 2; //文件为空
        }
    }

    /*
    获取某个用户的所有logo
     */
    public List<Logo> getMyLogoList(Map<String, Object> map){
        String token = (String) map.get("token");
        if (!redisTokenManager.checkToken(token)){
            return null;          //token无效或者过期
        }

        ObjectId userId = redisTokenManager.getUserId(token);
        Query query1 = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query1, User.class);
        assert user != null;
        List<ObjectId> myLogoIdList = user.getLogoList();

        Query query2 = Query.query(Criteria.where("logoId").in(myLogoIdList));
        List<Logo> myLogoList = mongoTemplate.find(query2, Logo.class);
        return myLogoList;
    }


    public boolean modifyPassword(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return false; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        assert user != null;
        String newPassword = (String) map.get("newPassword");
        user.setPassword(newPassword);

        mongoTemplate.save(user);
        return true;

    }



    public boolean modifyEmail(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return false; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        assert user != null;
        String newEmail = (String) map.get("newEmail");
        user.setEmail(newEmail);

        mongoTemplate.save(user);
        return true;

    }

    public boolean modifyPhone(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return false; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        assert user != null;
        String newPhone = (String) map.get("newPhone");
        user.setPhone(newPhone);

        mongoTemplate.save(user);
        return true;
    }


}
