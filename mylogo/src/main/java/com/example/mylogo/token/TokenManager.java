package com.example.mylogo.token;

import com.example.mylogo.entity.User;
import org.bson.types.ObjectId;

public interface TokenManager {

    public String createToken(ObjectId userId);

    public boolean checkToken(String token);

    public void delToken(String token);

    public ObjectId getUserId(String token);


}
