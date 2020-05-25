package com.example.mylogo.entity;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogoMeta {

    // 联系Logo与LogoMeta
    public ObjectId logoId;
    // 依次放:
    //                "era": 0.7,
    //                "maturity": 0.4,
    //                "weight": 0.5,
    //                "personality": 0.5,
    //                "definition": 0.7,
    //                "concept": 0.6
    public ArrayList<Double> meta;

    public LogoMeta(ArrayList<Double> meta, ObjectId logoid, ObjectId metaId) {
        this.logoId = logoid;
        this.meta = meta;
    }

    // LogoMeta -> map
    public Map<String, Object> ToMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("logoId", this.logoId);
        map.put("meta", this.meta);
        return map;
    }

    // map -> LogoMeta.
    public LogoMeta(Map<String, Object> map){
        ArrayList<Double> meta = new ArrayList<>();
        meta.add((Double)map.get("era"));
        meta.add((Double)map.get("maturity"));
        meta.add((Double)map.get("weight"));
        meta.add((Double)map.get("personality"));
        meta.add((Double)map.get("definition"));
        meta.add((Double)map.get("concept"));
        this.meta = meta;
        this.logoId = (ObjectId)map.get("logoId");
    }

    public LogoMeta(ObjectId logoId){
        ArrayList<Double> meta = new ArrayList<>(Arrays.asList(
            0.7, 0.4, 0.5, 0.5, 0.7, 0.6
        ));

        this.meta = meta;
        this.logoId = logoId;
    }





}
