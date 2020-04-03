package com.example.logodesign.token;

import com.example.logodesign.entity.User;
import org.bson.types.ObjectId;

public interface TokenManager {

    public String createToken(ObjectId userId);

    public boolean checkToken(String token);

    public void delToken(String token);

    public ObjectId getUserId(String token);


}
