package com.example.mylogo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {
    private ObjectId userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String url; //用户的icon头像在服务器的路径
    private List<ObjectId> logoList;
    private List<ObjectId> markedLogoList;
    private List<ObjectId> starLogoList;

    public User() {
        userId = new ObjectId();
        logoList = new ArrayList<>();
        markedLogoList = new ArrayList<>();
        starLogoList = new ArrayList<>();
    }



    public ObjectId getUserId() {
        return userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<ObjectId> getLogoList() {
        return logoList;
    }

    public void setLogoList(List<ObjectId> logoList) {
        this.logoList = logoList;
    }

    //添加自己的logo
    public void addLogo(ObjectId objectId){
        logoList.add(objectId);
    }

    public void delLogo(ObjectId objectId){
        logoList.remove(objectId);
    }


    //收藏的logo
    public List<ObjectId> getMarkedLogoList() {
        return markedLogoList;
    }

    public void setMarkedLogoList(List<ObjectId> markedLogoList) {
        this.markedLogoList = markedLogoList;
    }

    public void addMarkedLogo(ObjectId objectId){
        markedLogoList.add(objectId);
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<ObjectId> getStarLogoList() {
        return starLogoList;
    }

    public void setStarLogoList(List<ObjectId> starLogoList) {
        this.starLogoList = starLogoList;
    }

    public void addStarLogo(ObjectId objectId){
        starLogoList.add(objectId);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
