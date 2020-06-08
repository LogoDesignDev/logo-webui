package com.example.mylogo.dao;

import com.example.mylogo.entity.Gallery;
import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.User;
import com.example.mylogo.token.RedisTokenManager;
import io.netty.handler.codec.base64.Base64Decoder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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


    //注册用户
    public int registUser(Map<String, Object> map){
        String username = (String) map.get("username");
        String phone = (String) map.get("phone");
        String email = (String) map.get("email");
        Query queryPhone = Query.query(Criteria.where("phone").is(phone));
        Query queryEmail = Query.query(Criteria.where("email").is(email));
        Query queryUsername = Query.query(Criteria.where("username").is(username));
        if(mongoTemplate.findOne(queryPhone, User.class) != null){
            return 1;
        }
        if(mongoTemplate.findOne(queryEmail, User.class) != null){
            return 2;
        }
        if(mongoTemplate.findOne(queryUsername, User.class) != null){
            return 3;
        }
        User user = new User();
        user.setUsername((String)map.get("username"));
        user.setPassword((String)map.get("password"));
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserPicUrl("/icon/default.jpg");
        Gallery gallery = new Gallery();
        user.setGallery(gallery.getGalleryId());
        mongoTemplate.save(user,"user");
        mongoTemplate.save(gallery,"gallery");
        return 0;
    }


    //用户名登录
    public String login(Map<String, Object> map){
        String username = (String)(map.get("username"));
        String password = (String)(map.get("password"));
        Query query = Query.query(Criteria.where("username").is(username));

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
            ObjectId userId = redisTokenManager.getUserId(token);

            Query query = Query.query(Criteria.where("userId").is(userId));
            User user = mongoTemplate.findOne(query, User.class);
            String path = "/root/icon/"; //路径， 需要改变  /icon头像

            String imgName = userId.toString() + ".jpg";
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] b = decoder.decode(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String url = path + imgName;
            OutputStream out = new FileOutputStream(url);
            out.write(b);
            out.flush();
            out.close();
            user.setUserPicUrl("/icon/" + imgName);
            mongoTemplate.save(user);
            return 0; //成功存图片
        } else {
            return 2; //base64无效
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


    public int modifyPassword(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null){
            return 2; //user不存在
        }
        String oldPassword = (String) map.get("oldPwd");
        if(!oldPassword.equals(user.getPassword())){
            return 3; //旧密码不符合
        }
        String newPassword = (String) map.get("newPwd");
        user.setPassword(newPassword);

        mongoTemplate.save(user);
        return 0;
    }



    public int modifyEmail(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null){
            return 2;
        }
        String newEmail = (String) map.get("newEmail");
        user.setEmail(newEmail);

        mongoTemplate.save(user);
        return 0;

    }

    public int modifyPhone(Map<String, Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 1; //无效或者过期
        }
        ObjectId userId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(userId));

        User user = mongoTemplate.findOne(query, User.class);
        if(user == null){
            return 2;
        }
        String newPhone = (String) map.get("newPhone");
        user.setPhone(newPhone);

        mongoTemplate.save(user);
        return 0;
    }

    /*
    获取所有的用户
     */
    public List<User> getAllUser(Map<String, Object>map){
        /*
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }*/

        return mongoTemplate.findAll(User.class);
    }

    /*
    关注列表
     */
    public ArrayList<Map> getFocusList(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);
        List<ObjectId> list = user.getFocusList();
        ArrayList<Map> list1 = new ArrayList<>();
        for(ObjectId objectId : list){
            HashMap<String,Object> res = new HashMap<>();
            Query query1 = Query.query(Criteria.where("userId").is(objectId));
            User user2 = mongoTemplate.findOne(query1, User.class);
            res.put("uid",user2.getUserId().toString());
            res.put("userPicUrl",user2.getUserPicUrl());
            res.put("username",user2.getUsername());
            list1.add(res);
        }
        return list1;
    }

    /*
    粉丝列表
     */
    public ArrayList<Map> getFansList(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);
        List<ObjectId> list = user.getFansList();
        ArrayList<Map> list1 = new ArrayList<>();
        for(ObjectId objectId : list){
            HashMap<String,Object> res = new HashMap<>();
            Query query1 = Query.query(Criteria.where("userId").is(objectId));
            User user2 = mongoTemplate.findOne(query1, User.class);

            res.put("uid",user2.getUserId().toString());
            res.put("userPicUrl",user2.getUserPicUrl());
            res.put("username",user2.getUsername());
            list1.add(res);
        }
        return list1;
    }

    /*
    关注用户
     */
    public String follow(Map<String, Object>map){
        String id = (String) map.get("uid");
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        ObjectId userid=redisTokenManager.getUserId(token);

        //查询关注人
        Query query = Query.query(Criteria.where("userId").is(userid));
        User user = mongoTemplate.findOne(query, User.class);
        //查询被关注人
        Query query1=Query.query(Criteria.where("userId").is(id));
        User user1 = mongoTemplate.findOne(query1,User.class);

        user.addFocus(new ObjectId(id));
        user1.addFans(userid);

        user.setFocusCount(user.getFocusCount()+1);
        user1.setFansCount(user1.getFansCount()+1);
        mongoTemplate.save(user);
        mongoTemplate.save(user1);
        return id;
    }

    /*
    取消关注用户
     */
    public String unfollow(Map<String, Object>map){
        String id = (String) map.get("uid");
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        ObjectId userid=redisTokenManager.getUserId(token);

        //查询取消关注人
        Query query = Query.query(Criteria.where("userId").is(userid));
        User user = mongoTemplate.findOne(query, User.class);

        //查询被取消关注人
        Query query1=Query.query(Criteria.where("userId").is(id));
        User user1 = mongoTemplate.findOne(query1,User.class);

        user.removeFocus(new ObjectId(id));
        user1.removeFans(userid);
        user.setFocusCount(user.getFocusCount()-1);
        user1.setFansCount(user1.getFansCount()-1);
        mongoTemplate.save(user);
        mongoTemplate.save(user1);
        return id;
    }

    /*
    用户作品数
     */
    public int prodCount(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);
        if(user==null){
            return -1;
        }
        return user.getProdCount();
    }

    /*
    用户粉丝数
     */
    public int fansCount(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);

        if(user==null){
            return -1;
        }
        return user.getFansCount();
    }

    /*
    用户关注数
     */
    public int focusCount(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);

        if(user==null){
            return -1;
        }
        return user.getFocusCount();
    }

    /*
    用户被赞数
     */
    public int belikedCount(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);

        if(user==null){
            return -1;
        }
        return user.getBeLikedCount();
    }

    /*
    用户被收藏数
     */
    public int beMarkedCount(Map<String, Object>map){
        String id = (String) map.get("uid");
        Query query = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query, User.class);

        if(user==null){
            return -1;
        }
        return user.getBeMarkedCount();
    }


    /*
    根据关键词搜索用户
     */
    public List<User> findUserByKeyword(Map<String, Object>map){
        String keyword = (String) map.get("keyword");
        int order = (int) map.get("order");
        Query query = new Query();
        query.addCriteria(Criteria.where("username").regex(".*?" + keyword + ".*?"));
        query.fields().include("userId")
                .include("username")
                .include("userPicUrl")
                .include("beLikedCount")
                .include("beMarkedCount")
                .include("fansCount")
                .include("logoList");

        List<User> list = mongoTemplate.find(query, User.class);
        Comparator<User> comparator = null;
        if(order == 1){
            comparator = new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return  o2.getUserId().getTimestamp() - o1.getUserId().getTimestamp();
                }
            };
        }
        else if(order == 2){
            comparator = new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getFansList().size()  - o1.getFansList().size();
                }
            };
        }
        else if(order == 3){
            comparator = new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getBeLikedCount() - o1.getBeLikedCount();
                }
            };
        }
        else if(order == 4){
            comparator = new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getBeMarkedCount() - o1.getBeMarkedCount();
                }
            };
        }
        else if(order == 5){
            comparator = new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getLogoList().size() - o1.getLogoList().size();
                }
            };
        }
        if(comparator != null){
            Collections.sort(list, comparator);
        }

        return list;
    }
}
