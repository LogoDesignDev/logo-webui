package com.example.mylogo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private ObjectId userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer focusCount;//关注数
    private Integer fansCount;//粉丝数
    private Integer prodCount;//用户作品数
    private String userPicUrl;//用户头像url
    private List<ObjectId> gallery;   //用户最多5个图库
    private int galleryidCount; //计算图库数量

    private List<ObjectId> logoList;
    private List<ObjectId> markedLogoList;
    private List<ObjectId> starLogoList;
    private Integer beLikedCount;   //被赞数
    private Integer beMarkedCount;  //被收藏数
    private List<ObjectId> focusList;//关注列表
    private List<ObjectId> fansList;//粉丝列表

    public User() {
        userId = new ObjectId();
        logoList = new ArrayList<>();
        markedLogoList = new ArrayList<>();
        starLogoList = new ArrayList<>();
        beLikedCount = 0;
        beMarkedCount = 0;
        galleryidCount = 0;
        focusCount = 0;
        prodCount = 0;
        fansCount = 0;
        focusList = new ArrayList<>();
        fansList = new ArrayList<>();
        gallery = new ArrayList<>();
    }

    //设置图库id
    public void setGallery(ObjectId galleryid){
        this.gallery.add(galleryid);
    }

    public void delGallery(ObjectId galleryid){
        this.gallery.remove(galleryid);
    }

    public Integer getProdCount(){return prodCount;}

    public Integer getFansCount(){return fansCount;}

    public Integer getFocusCount(){return focusCount;}

    public List<ObjectId> getGallery(){return gallery;}

    public String getUserPicUrl(){return userPicUrl;}

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

    //粉丝改变
    public void addFans(ObjectId objectId){fansList.add(objectId);}

    public void removeFans(ObjectId objectId){fansList.remove(objectId);}

    //关注用户改变
    public void addFocus(ObjectId objectId){focusList.add(objectId);}

    public void removeFocus(ObjectId objectId){focusList.remove(objectId);}

    public Integer getBeLikedCount() {
        return beLikedCount;
    }

    public void setBeLikedCount(Integer beLikedCount) {
        this.beLikedCount = beLikedCount;
    }

    public Integer getBeMarkedCount() {
        return beMarkedCount;
    }

    public void setBeMarkedCount(Integer beMarkedCount) {
        this.beMarkedCount = beMarkedCount;
    }

    public List<ObjectId> getFocusList() {
        return focusList;
    }

    public void setFocusList(List<ObjectId> focusList) {
        this.focusList = focusList;
    }

    public List<ObjectId> getFansList() {
        return fansList;
    }

    public void setFansList(List<ObjectId> fansList) {
        this.fansList = fansList;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public void setProdCount(Integer prodCount) {
        this.prodCount = prodCount;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public void setGallery(List<ObjectId> gallery) {
        this.gallery = gallery;
    }

    public int getGalleryidCount() {
        return galleryidCount;
    }

    public void setGalleryidCount(int galleryidCount) {
        this.galleryidCount = galleryidCount;
    }
}
