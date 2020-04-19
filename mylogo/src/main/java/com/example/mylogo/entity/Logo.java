package com.example.mylogo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logo")
public class Logo {
    private ObjectId logoId;
    private String title;
    private ObjectId authorId;
    private boolean published; //是否是已经发布的或者私有
    private String introduce; //logo的介绍
    private String url; //图片的路径

    public Logo() {
        published = false;
    }

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


    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
