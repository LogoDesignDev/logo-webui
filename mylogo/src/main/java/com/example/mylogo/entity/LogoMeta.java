package com.example.mylogo.entity;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class LogoMeta {
    public ArrayList<Double> meta;

    // logoId -> logo -> authorId -> User.
    public ObjectId logoId;

    public LogoMeta(ArrayList<Double> meta, ObjectId logoid) {
        this.logoId = logoid;
        this.meta = meta;
    }

}
