package com.example.mylogo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "logo")
public class Logo {

    @Id
    private ObjectId logoId;
    private String lId; // logoId的toString
    private String title;
    private ObjectId authorId;
    private String introduce; //logo的介绍
    private String url; //图片的路径
    private String name;//图片的名字
    private String authorName;//作者名字
    private String authorUrl;//作者头像url
    private int like;//点赞数
    private int collect;//收藏数
    private String publishedTime;//发布时间

    public Logo() {
        logoId = new ObjectId();
        lId = logoId.toString();
        like = 0;
        collect = 0;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        publishedTime = formatter.format(date);
    }
    public void setAuthorUrl(String url){this.authorUrl = url;}

    public String getAuthorUrl(){return authorUrl;}

    public void setAuthorName(String name){this.authorName = name;}

    public String getAuthorName(){return authorName;}

    public void setPublishedTime(String time){this.publishedTime = time;}

    public String getPublishedTime(){return publishedTime;}

    public int getLike(){return like;}

    public int getCollect(){return collect;}

    public void setLike(int like){this.like=like;}

    public void setCollect(int collect){this.collect=collect;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public ObjectId getLogoId() {
        return logoId;
    }

    public void setId(ObjectId logoId) {
        this.logoId = logoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(ObjectId authorId) {
        this.authorId = authorId;
    }

    public void setLogoId(ObjectId logoId) {
        this.logoId = logoId;
    }
}
