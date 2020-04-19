package com.example.mylogo.dao;

import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import com.example.mylogo.token.RedisTokenManager;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class LogoTemplate {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RedisTokenManager redisTokenManager;

    @Autowired
    UserTemplate userTemplate;

    /*
    创建Logo，要基于机器学习, 返回给前端base64，
     */
    public String createLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){ //token失效或者过期
            return "null";
        }

        String url = "";
        /*

        这里机器学习生成logo
        返回一张图片
        这张图片存在服务器的地址里
        返回这张图片的url给前端
         */


        return url;
    }


    /*
    保存Logo

     */
    public int saveLogo(Map<String, Object> map) throws Exception {
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){ //token失效或者过期
            return 1;
        }


        String base64 = (String) map.get("base64");
        if (base64 != null && !"".equals(base64)) {
            ObjectId id = redisTokenManager.getUserId(token);
            String path = "/usr/share/nginx/html/image/"; //路径， 需要改变  /icon头像
            String logoName = (String) map.get("logoName");

            String imgName = id.toString() + logoName + ".jpg";
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] b = decoder.decode(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path + imgName);
            out.write(b);
            out.flush();
            out.close();
            Logo logo = new Logo();
            logo.setUrl("/usr/share/nginx/html/image/" + imgName);

            //通过token 获取user
            ObjectId userId = redisTokenManager.getUserId(token);
            Query query = Query.query(Criteria.where("userId").is(userId));
            User user = mongoTemplate.findOne(query, User.class);
            assert user != null;

            user.addLogo(logo.getLogoId());
            logo.setAuthorId(user.getUserId());
            mongoTemplate.save(logo, "logo");
            mongoTemplate.save(user, "user");
            return 0; //成功存图片
        } else {
            return 2; //文件为空
        }

    }



    /*
    删除自己的Logo
    在list里面删除logoId
     */
    public int delLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1;
        }

        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        assert user != null;

        String logoId = (String) map.get("logoId");
        user.delLogo(new ObjectId(logoId));
        mongoTemplate.save(user, "user");

        return 0;
    }

    /*
    发布logo到社区里
     */
    public int publishLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1;
        }

        String logoId = (String) map.get("logoId");
        Query query = Query.query(Criteria.where("logoId").is(logoId));
        Logo logo = mongoTemplate.findOne(query, Logo.class);
        assert logo != null; //确保logo存在
        logo.setPublished(true);
        mongoTemplate.save(logo, "logo");
        return 0;
    }


    /*
    获取所有已发布的logo
    存在mongo
     */
    public List<Logo> getAllPublishedLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        Query query = Query.query(Criteria.where("published").is(true));
        //获取所有的已发布的logo
        return mongoTemplate.find(query, Logo.class);
    }

}
