package com.example.logodesign.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logo")
public class Logo {
    private ObjectId id;
    private String title;
    private ObjectId authorId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
}
