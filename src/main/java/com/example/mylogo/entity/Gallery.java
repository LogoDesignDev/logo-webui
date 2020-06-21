package com.example.mylogo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "gallery")
public class Gallery {
    @Id
    private ObjectId galleryId;//图库id
    private String name;//图库名称
    private String region;//图库权限
    private String createtime;//创建时间
    private String updatetime;//修改时间
    private List<ObjectId> logoList;

    public Gallery(){
        galleryId = new ObjectId();
        name = new String();
        logoList = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        createtime = formatter.format(date);
        updatetime = formatter.format(date);
    }

    public ObjectId getGalleryId(){return galleryId;}

    public String getCreatetime(){return createtime;}

    public String getName(){return name;}

    public void setUpdatetime(String updatetime){this.updatetime=updatetime;}

    public void setName(String name){this.name=name;}

    public void setRegion(String region){this.region=region;}
    //往图库添加logo
    public void addLogo(ObjectId objectId){
        logoList.add(objectId);
    }

    public void delLogo(ObjectId objectId){
        logoList.remove(objectId);
    }

    public void setLogoList(List<ObjectId> list){this.logoList=list;}

    public List<ObjectId> getlogoList(){return logoList;}
}
