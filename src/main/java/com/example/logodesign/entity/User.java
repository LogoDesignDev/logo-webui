package com.example.logodesign.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
public class User {
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    private String phone;
    //private String iconAddress; 头像的地址  服务器的固定地址 + id
    private List<ObjectId> LogoList;

    public ObjectId getUserId() {
        return id;
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

    public List<ObjectId> getLogoList() {
        return LogoList;
    }

    public void setLogoList(List<ObjectId> logoList) {
        LogoList = logoList;
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
}
