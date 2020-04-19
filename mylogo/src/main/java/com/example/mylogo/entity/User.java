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
    private List<ObjectId> logoList;
    private List<ObjectId> markedLogoList;

    public User() {
        userId = new ObjectId();
        logoList = new ArrayList<>();
        markedLogoList = new ArrayList<>();
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

}
