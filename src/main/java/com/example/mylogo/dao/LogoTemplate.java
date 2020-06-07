package com.example.mylogo.dao;

import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.LogoMeta;
import com.example.mylogo.entity.User;
import com.example.mylogo.entity.Gallery;
import com.example.mylogo.token.RedisTokenManager;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.URL;

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
    /* Yang:
        需要用户传入的参数（0-1之间的浮点数）:
                "era": 0.7,
                "maturity": 0.4,
                "weight": 0.5,
                "personality": 0.5,
                "definition": 0.7,
                "concept": 0.6
        过程：
            1. 获将参数填充 req_url
            2. 发起http请求获得response
            3. 将response(svg代码)转化为png图片
            4. 返回png文件的base64作为url

        注意:
            输入text的字符要少于9个英文\数字字符，超出部分显示不了.

        目前将图片保存在 logo-weibui/tmp.png中， 同时返回其base64值
         */
    public String createLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        /*
        if(!redisTokenManager.checkToken(token)){ //token失效或者过期
            return "null";
        }*/

        String url = "";

        final String port = "1234";
        Double era = (Double)map.getOrDefault("era",  0.7);
        era = Math.max(0.0, Math.min(1.0, era));

        Double maturity = (Double)map.getOrDefault("maturity", 0.4);
        maturity = Math.max(0.0, Math.min(1.0, maturity));

        Double weight = (Double)map.getOrDefault("weight", 0.5);
        weight = Math.max(0.0, Math.min(1.0, weight));

        Double personality = (Double)map.getOrDefault("personality",  0.5);
        personality = Math.max(0.0, Math.min(1.0, personality));

        Double definition = (Double)map.getOrDefault("definition",  0.7);
        definition = Math.max(0.0, Math.min(1.0, definition));

        Double concept = (Double)map.getOrDefault("concept", 0.6);
        concept = Math.max(0.0, Math.min(1.0, concept));

        Integer amount = (int)map.getOrDefault("amount", 1);
        String text = (String)map.getOrDefault("text", "default_world!");
        String req_url = "http://localhost:" + port + "/getOneFont?payload={%22type%22:%22all%22,%22era%22:%22" + era
                + "%22,%22maturity%22:%22" + maturity
                + "%22,%22weight%22:%22" + weight
                + "%22,%22personality%22:%22" + personality
                + "%22,%22definition%22:%22" + definition
                + "%22,%22concept%22:%22" + concept
                + "%22}&amountNear=" + amount + "&text_str=" + text;

        // 发起http请求，获取svg代码
        System.out.println(req_url);
        String svgCode = null;
        try{
            URL req = new URL(req_url);
            HttpURLConnection cont = (HttpURLConnection)req.openConnection();
            cont.setConnectTimeout(500); // 500ms
            cont.connect();
            if (cont.getResponseCode() == 200){
                InputStream istream = cont.getInputStream();
                if (istream != null){
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(istream, "UTF-8"));
                    svgCode = bufr.readLine();
                    svgCode = svgCode.substring(1, svgCode.length() - 1);
                    //svgCode = "<svg class=\"font-svg-class\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" viewBox=\"0 0 300 300\" version=\"1.1\" xmlns:svgjs=\"http://svgjs.com/svgjs\"><g transform=\"matrix(1,0,0,1,0,150)\"><path d=\"M29.71-14.06L29.71-27.95Q28.41-28.65 27.28-29.14Q26.16-29.64 25.07-29.95Q23.98-30.27 22.85-30.43Q21.73-30.59 20.39-30.59L20.39-30.59Q18.39-30.59 16.56-29.67Q14.73-28.76 13.31-27.04Q11.88-25.31 11.04-22.83Q10.20-20.36 10.20-17.23L10.20-17.23Q10.20-11.07 12.29-7.91Q14.38-4.75 18.98-4.75L18.98-4.75Q20.60-4.75 22.20-5.55Q23.80-6.36 25.22-7.66Q26.65-8.96 27.81-10.63Q28.97-12.30 29.71-14.06L29.71-14.06ZM36.21-56.25L36.21-18.63Q36.21-11.99 36.97-7.47Q37.72-2.95 38.85 0L38.85 0L32.52 0Q32.13-0.91 31.59-2.72Q31.04-4.54 30.59-6.50L30.59-6.50Q28.05-2.92 24.86-1.02Q21.66 0.88 17.75 0.88L17.75 0.88Q14.52 0.88 11.94-0.33Q9.35-1.55 7.51-3.78Q5.66-6.01 4.68-9.16Q3.69-12.30 3.69-16.17L3.69-16.17Q3.69-20.85 4.97-24.61Q6.26-28.37 8.51-31.03Q10.76-33.68 13.80-35.12Q16.84-36.56 20.39-36.56L20.39-36.56Q21.52-36.56 22.73-36.32Q23.94-36.07 25.15-35.65Q26.37-35.23 27.53-34.68Q28.69-34.14 29.71-33.57L29.71-33.57L29.71-43.42Q29.71-47.67 29.51-50.85Q29.32-54.04 28.83-56.25L28.83-56.25L36.21-56.25ZM76.61-17.05L51.68-17.05Q51.79-13.96 52.65-11.67Q53.51-9.39 55.00-7.89Q56.50-6.40 58.52-5.66Q60.54-4.92 62.93-4.92L62.93-4.92Q66.34-4.92 69.61-5.70Q72.88-6.47 76.11-8.44L76.11-8.44L76.82-2.64Q74.95-1.69 73.16-1.02Q71.37-0.35 69.57 0.07Q67.78 0.49 65.94 0.69Q64.09 0.88 62.05 0.88L62.05 0.88Q58.68 0.88 55.62-0.30Q52.56-1.48 50.24-3.78Q47.92-6.08 46.55-9.49Q45.18-12.90 45.18-17.40L45.18-17.40Q45.18-21.76 46.35-25.29Q47.53-28.83 49.71-31.34Q51.89-33.86 55.02-35.21Q58.15-36.56 62.05-36.56L62.05-36.56Q64.93-36.56 67.15-35.88Q69.36-35.19 70.98-33.98Q72.60-32.77 73.69-31.15Q74.78-29.53 75.43-27.67Q76.08-25.80 76.36-23.78Q76.64-21.76 76.64-19.79L76.64-19.79L76.64-18.42Q76.64-17.54 76.61-17.05L76.61-17.05ZM61.52-31.29L61.52-31.29Q57.66-31.29 55.20-29.02Q52.73-26.75 51.96-21.97L51.96-21.97L70.31-21.97Q70.31-24.22 69.66-25.96Q69.01-27.70 67.83-28.90Q66.66-30.09 65.04-30.69Q63.42-31.29 61.52-31.29ZM81.74-29.88L81.74-34.98L88.77-34.98L88.77-41.13Q88.77-49.04 91.99-53.00Q95.20-56.95 100.90-56.95L100.90-56.95Q102.97-56.95 104.98-56.30Q106.98-55.65 108.63-54.49L108.63-54.49L107.05-48.87Q105.71-49.85 104.08-50.41Q102.45-50.98 100.90-50.98L100.90-50.98Q98.16-50.98 96.71-49.01Q95.27-47.04 95.27-42.54L95.27-42.54L95.27-34.98L104.77-34.98L104.77-29.88L95.27-29.88L95.27-17.37Q95.27-14.80 95.33-12.29Q95.38-9.77 95.50-7.51Q95.63-5.24 95.84-3.30Q96.05-1.37 96.33 0L96.33 0L88.77 0L88.77-29.88L81.74-29.88ZM135-25.84L135-30.30Q134.58-30.41 134.05-30.52Q133.52-30.62 132.80-30.73Q132.08-30.83 131.12-30.88Q130.15-30.94 128.85-30.94L128.85-30.94Q125.40-30.94 123.10-29.55Q120.80-28.16 119.43-25.86Q118.05-23.55 117.47-20.57Q116.89-17.58 116.89-14.41L116.89-14.41Q116.89-12.38 117.30-10.67Q117.70-8.96 118.41-7.72Q119.11-6.47 120.02-5.78Q120.94-5.10 121.99-5.10L121.99-5.10Q123.54-5.10 125.10-6.29Q126.67-7.49 128.11-9.37Q129.55-11.25 130.82-13.59Q132.08-15.93 133.01-18.23Q133.95-20.53 134.47-22.57Q135-24.61 135-25.84L135-25.84ZM143.79 0L137.64 0Q137.25-0.84 136.95-2.32Q136.65-3.80 136.46-5.47Q136.27-7.14 136.16-8.79Q136.05-10.44 136.05-11.60L136.05-11.60L136.05-14.41Q135-11.67 133.54-8.95Q132.08-6.22 130.20-4.04Q128.32-1.86 126-0.49Q123.68 0.88 120.94 0.88L120.94 0.88Q118.72 0.88 116.79-0.11Q114.86-1.09 113.43-3.02Q112.01-4.96 111.20-7.82Q110.39-10.69 110.39-14.41L110.39-14.41Q110.39-18.60 111.52-22.59Q112.64-26.58 114.96-29.69Q117.28-32.80 120.87-34.68Q124.45-36.56 129.38-36.56L129.38-36.56Q132.79-36.56 135.81-36.12Q138.83-35.68 141.50-34.98L141.50-34.98L141.50-18.63Q141.50-11.99 142.14-7.47Q142.77-2.95 143.79 0L143.79 0ZM185.45 0L179.47 0Q179.23-0.60 178.95-1.41Q178.66-2.21 178.40-3.13Q178.14-4.04 177.91-5.04Q177.68-6.05 177.54-7.03L177.54-7.03Q174.34-3.13 170.95-1.13Q167.55 0.88 163.83 0.88L163.83 0.88Q152.58 0.88 152.58-14.24L152.58-14.24L152.58-24.61Q152.58-26.30 152.56-27.67Q152.54-29.04 152.47-30.32Q152.40-31.61 152.26-32.94Q152.12-34.28 151.88-35.86L151.88-35.86L159.08-35.86L159.08-16.35Q159.08-13.36 159.38-11.20Q159.68-9.04 160.38-7.65Q161.09-6.26 162.23-5.59Q163.37-4.92 165.06-4.92L165.06-4.92Q166.61-4.92 168.36-5.85Q170.12-6.79 171.72-8.23Q173.32-9.67 174.64-11.41Q175.96-13.15 176.66-14.77L176.66-14.77L176.66-24.61Q176.66-26.33 176.64-27.72Q176.63-29.11 176.55-30.38Q176.48-31.64 176.34-32.94Q176.20-34.24 175.96-35.86L175.96-35.86L183.16-35.86L183.16-15.82Q183.16-10.69 183.74-6.84Q184.32-2.99 185.45 0L185.45 0ZM201.97-56.25L201.97-16.70Q201.97-15.01 202.03-12.73Q202.08-10.44 202.17-8.09Q202.25-5.73 202.38-3.59Q202.50-1.44 202.68 0L202.68 0L195.47 0L195.47-39.73Q195.47-44.86 195.29-49.03Q195.12-53.19 194.77-56.25L194.77-56.25L201.97-56.25ZM209.71-29.88L209.71-34.98L216.74-34.98L216.74-46.41L223.24-46.41L223.24-34.98L234.67-34.98L234.67-29.88L223.24-29.88L223.24-13.71Q223.24-11.00 223.51-9.28Q223.77-7.56 224.31-6.59Q224.86-5.63 225.70-5.27Q226.55-4.92 227.74-4.92L227.74-4.92Q229.25-4.92 231.15-5.59Q233.05-6.26 235.37-7.56L235.37-7.56L235.37-1.93Q232.95-0.35 230.73 0.26Q228.52 0.88 226.65 0.88L226.65 0.88Q224.58 0.88 222.79 0.40Q220.99-0.07 219.64-1.27Q218.29-2.46 217.51-4.45Q216.74-6.43 216.74-9.49L216.74-9.49L216.74-29.88L209.71-29.88Z\"></path></g><defs id=\"SvgjsDefs1012\"></defs></svg>\"";
                    String[] v = svgCode.split("\\\\");
                    svgCode = "";
                    for(int i = 0; i < v.length; i++){
                        svgCode = svgCode.concat(v[i]);
                    }
                    svgCode = svgCode.replaceAll("<g>", "<g transform=\"matrix(1,0,0,1,0,150)\">"); // 坐标变换
                    //System.out.println(svgCode);
                }
            }
            cont.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            // svg --> .png
            File file = new File("./tmp.png");
            FileOutputStream fo = new FileOutputStream(file);
            byte[] bs = svgCode.getBytes("utf-8");

            System.out.println("png lens:" + bs.length +  " byte");
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput inp = new TranscoderInput(new ByteArrayInputStream(bs));
            TranscoderOutput outp = new TranscoderOutput(fo);
            t.transcode(inp, outp);
            fo.flush();
            fo.close();

            // png file --> byte[] --> string(base64)
            file = new File("./tmp.png");
            FileInputStream fi = new FileInputStream(file);
            Base64.Encoder encoder = Base64.getEncoder();
            bs = fi.readAllBytes();
            System.out.println("input lens:" + bs.length +  " byte");
            url = new String(encoder.encode(bs));
        }catch (IOException e){
            e.printStackTrace();
        }catch (TranscoderException e){
            e.printStackTrace();
        }
        //System.out.println("base64:url = " + url);
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
            String path = "/root/image/"; //路径
            String logoName = (String) map.get("logoName");


            String imgName = id.toString() + logoName + ".png";
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
            logo.setUrl("/root/image/" + imgName);

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
     *获取用户的所有图库id
     */
    public ArrayList<Map> getAllGallery(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        ObjectId UserId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(UserId));
        User user  = mongoTemplate.findOne(query,User.class);
        assert user != null;
        ArrayList<Map> list = new ArrayList<>();
        List<ObjectId> galleryid = user.getGallery();
        for(ObjectId objectId : galleryid){
            HashMap<String,Object> res = new HashMap();
            Query query1 = Query.query(Criteria.where("galleryId").is(objectId));
            Gallery gallery = mongoTemplate.findOne(query1, Gallery.class);

            res.put("id",gallery.getGalleryId().toString());
            res.put("name",gallery.getName());
            list.add(res);
        }
        return list;
    }


    /*
     *获取图库的所有内容
     */
    public ArrayList<Map> getAllLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        String galleryId = (String) map.get("id");
        Query query = Query.query(Criteria.where("galleryId").is(galleryId));
        Gallery gallery = mongoTemplate.findOne(query,Gallery.class);

        List<ObjectId> list = gallery.getlogoList();
        ArrayList<Map> list1 = new ArrayList<>();
        for(ObjectId id : list){
            HashMap<String,Object> res = new HashMap<>();
            Query query1 = Query.query(Criteria.where("logoId").is(id));
            Logo logo = mongoTemplate.findOne(query1,Logo.class);

            res.put("id",logo.getLogoId().toString());
            res.put("imgUrl",logo.getUrl());
            list1.add(res);
        }
        return list1;
    }

    /*
    添加新的图库
     */
    public String addGallery(Map<String,Object> map){
        String token = (String) map.get("token");
        String name = (String)map.get("name");
        String region = (String)map.get("region");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        ObjectId UserId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(UserId));
        User user  = mongoTemplate.findOne(query,User.class);
        assert user != null;
        Gallery gallery = new Gallery();
        gallery.setName(name);
        gallery.setRegion(region);
        user.setGallery(gallery.getGalleryId());
        mongoTemplate.save(gallery,"gallery");
        mongoTemplate.save(user);
        return gallery.getCreatetime();
    }

    /*
    更新图库信息
     */
    public String updateGallery(Map<String,Object> map){
        String token = (String) map.get("token");
        String id = (String)map.get("id");
        String region = (String)map.get("region");
        String name = (String)map.get("name");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String updatetime = formatter.format(date);
        Query query = Query.query(Criteria.where("galleryId").is(id));
        Gallery gallery = mongoTemplate.findOne(query,Gallery.class);
        gallery.setName(name);
        gallery.setRegion(region);
        gallery.setUpdatetime(updatetime);
        mongoTemplate.save(gallery);
        return updatetime;
    }

    /*
    删除图库
     */
    public int delGallery(Map<String,Object> map){
        String token = (String) map.get("token");
        String id = (String)map.get("id");
        if(!redisTokenManager.checkToken(token)){
            return 0;
        }
        ObjectId UserId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("userId").is(UserId));
        User user  = mongoTemplate.findOne(query,User.class);
        assert user != null;
        user.delGallery(new ObjectId(id));

        Query query1 = Query.query(Criteria.where("galleryId").is(id));
        Gallery gallery = mongoTemplate.findOne(query1,Gallery.class);

        mongoTemplate.remove(gallery);
        mongoTemplate.save(user);
        return 1;
    }

    /*
    获取logo详情
     */
    public Logo getLogoInfo(Map<String,Object> map){
        String token = (String) map.get("token");
        String logoid = (String)map.get("logoid");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        Query query = Query.query(Criteria.where("logoId").is(logoid));
        Logo logo  = mongoTemplate.findOne(query,Logo.class);
        assert logo != null;
        return logo;
    }


    /*
     *添加新的logo
     */
    public HashMap<String,Object> addLogo(Map<String,Object>map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        String id = (String) map.get("galleryid");
        String logoUrl = (String) map.get("imgUrl");
        String logoname = (String) map.get("name");
        String logoauthor = (String) map.get("author");

        Logo logo = new Logo();
        logo.setUrl(logoUrl);
        logo.setName(logoname);
        logo.setAuthorName(logoauthor);

        Query query1 = Query.query(Criteria.where("galleryId").is(id));
        Gallery gallery = mongoTemplate.findOne(query1,Gallery.class);
        assert gallery != null;

        gallery.addLogo(logo.getLogoId());

        HashMap<String,Object> res = new HashMap<>();
        res.put("logoid",logo.getLogoId().toString());
        res.put("name",logo.getName());
        res.put("author",logo.getAuthorName());
        res.put("imgUrl",logo.getUrl());
        mongoTemplate.save(gallery);
        mongoTemplate.save(logo,"logo");

        return res;
    }

    /*
    删除图库logo
    */
    public int delGalleryLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return 0;
        }

        String id = (String) map.get("galleryid");
        String logoId = (String) map.get("logoid");
        Query query = Query.query(Criteria.where("galleryId").is(id));
        Gallery gallery = mongoTemplate.findOne(query,Gallery.class);
        assert gallery != null;

        gallery.delLogo(new ObjectId(logoId));
        mongoTemplate.save(gallery);

        return 1;
    }

    /*
   收藏logo
   */
    public Logo collectLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        String id = redisTokenManager.getUserId(token).toString();
        String logoId = (String) map.get("logoid");

        Query query = Query.query(Criteria.where("logoId").is(logoId));
        Logo logo = mongoTemplate.findOne(query,Logo.class);
        assert logo != null;

        Query query1 = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query1,User.class);
        user.addMarkedLogo(new ObjectId(logoId));
        mongoTemplate.save(user);

        logo.setCollect(logo.getCollect()+1);

        ObjectId userId = logo.getAuthorId();
        Query query2 = Query.query(Criteria.where("userId").is(userId));
        User user1 = mongoTemplate.findOne(query2,User.class);
        user1.setBeMarkedCount(user1.getBeMarkedCount()+1);

        mongoTemplate.save(logo);
        mongoTemplate.save(user1);

        return logo;
    }

    /*
   点赞logo
   */
    public Logo likeLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        String logoId = (String) map.get("logoid");

        Query query = Query.query(Criteria.where("logoId").is(logoId));
        Logo logo = mongoTemplate.findOne(query,Logo.class);
        assert logo != null;

        ObjectId userId = logo.getAuthorId();
        Query query1 = Query.query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query1,User.class);
        assert user != null;
        user.setBeLikedCount(user.getBeLikedCount()+1);
        mongoTemplate.save(user);

        String userId1 = redisTokenManager.getUserId(token).toString();
        Query query2 = Query.query(Criteria.where("userId").is(userId1));
        User user1 = mongoTemplate.findOne(query2,User.class);

        user1.addStarLogo(new ObjectId(logoId));

        logo.setLike(logo.getLike()+1);


        mongoTemplate.save(logo);
        mongoTemplate.save(user1);

        return logo;
    }

    /*
   取消收藏logo
   */
    public Logo delcollectLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        String id = redisTokenManager.getUserId(token).toString();
        String logoId = (String) map.get("logoid");

        Query query = Query.query(Criteria.where("logoId").is(logoId));
        Logo logo = mongoTemplate.findOne(query,Logo.class);
        assert logo != null;

        Query query1 = Query.query(Criteria.where("userId").is(id));
        User user = mongoTemplate.findOne(query1,User.class);
        user.delMarkedLogo(new ObjectId(logoId));
        mongoTemplate.save(user);

        logo.setCollect(logo.getCollect()-1);

        ObjectId userId = logo.getAuthorId();
        Query query2 = Query.query(Criteria.where("userId").is(userId));
        User user1 = mongoTemplate.findOne(query2,User.class);
        user1.setBeMarkedCount(user1.getBeMarkedCount()-1);

        mongoTemplate.save(logo);
        mongoTemplate.save(user1);

        return logo;
    }

    /*
   取消点赞logo
   */
    public Logo dellikeLogo(Map<String,Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }

        String logoId = (String) map.get("logoid");

        Query query = Query.query(Criteria.where("logoId").is(logoId));
        Logo logo = mongoTemplate.findOne(query,Logo.class);
        assert logo != null;

        ObjectId userId = logo.getAuthorId();
        Query query1 = Query.query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query1,User.class);
        assert user != null;

        user.setBeLikedCount(user.getBeLikedCount()-1);
        mongoTemplate.save(user);

        String userId1 = redisTokenManager.getUserId(token).toString();
        Query query2 = Query.query(Criteria.where("userId").is(userId1));
        User user1 = mongoTemplate.findOne(query2,User.class);

        user1.delStarLogo(new ObjectId(logoId));
        logo.setLike(logo.getLike()-1);


        mongoTemplate.save(logo);
        mongoTemplate.save(user1);

        return logo;
    }

    /*
    获取所有已发布的logo
    存在mongo
     */
    public List<Logo> getAllPublishedLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        /*
        if(!redisTokenManager.checkToken(token)){
            return null;
        }*/

        Query query = Query.query(Criteria.where("published").is(true));
        //获取所有的已发布的logo
        return mongoTemplate.find(query, Logo.class);
    }



    /*
    TODO: 2020-05-21
    获取一个Logo
     */
    public Logo getPublishedLogo(Map<String, Object> map){
        String token = (String) map.get("token");
        if(!redisTokenManager.checkToken(token)){
            return null;
        }
        ObjectId logoId = redisTokenManager.getUserId(token);
        Query query = Query.query(Criteria.where("logoId").is(logoId));
        return mongoTemplate.findOne(query, Logo.class);
    }

    /*
    TODO: 2020-05-21
    保存meta到 redis.
     */
    public void saveLogoMeta(LogoMeta meta) throws Exception{

    }

    /*
   根据关键词搜索Logo
    */
    public List<Logo> findLogoByKeyword(Map<String, Object>map){
        String keyword = (String) map.get("keyword");
        int order = (int) map.get("order");
        Query query = Query.query(Criteria.where("name").regex(".*?" + keyword + ".*?"));
        List<Logo> list = mongoTemplate.find(query, Logo.class);
        Comparator<Logo> comparator = null;
        if(order == 1){
            comparator = new Comparator<Logo>() {
                @Override
                public int compare(Logo o1, Logo o2) {
                    return  o2.getLogoId().getTimestamp() - o1.getLogoId().getTimestamp();
                }
            };
        }
        else if(order == 2){
            comparator = new Comparator<Logo>() {
                @Override
                public int compare(Logo o1, Logo o2) {
                    return o2.getLike()  - o1.getLike();
                }
            };
        }
        else if(order == 3){
            comparator = new Comparator<Logo>() {
                @Override
                public int compare(Logo o1, Logo o2) {
                    return o2.getCollect() - o1.getCollect();
                }
            };
        }
        if(comparator != null){
            Collections.sort(list, comparator);
        }

        return list;
    }

    /*
    上传图片
    */
    public String fileUpload(Map<String,Object> map){
        MultipartFile file =(MultipartFile) map.get("file");
        if(file.isEmpty()){
            return null;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "/root/image"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String real = filePath +"/"+ fileName;
        File dest = new File(real);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return real;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
