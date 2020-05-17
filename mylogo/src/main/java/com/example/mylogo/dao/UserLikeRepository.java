package com.example.mylogo.dao;

import com.example.mylogo.extra.UserLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLikeRepository {
    public UserLike save(UserLike userLike){
        return userLike;
    }

    public List<UserLike> saveAll(List<UserLike> list){
        return list;
    }

    public Page<UserLike> findByLikedUserIdAndStatus(String likedUserId, int a, Pageable pageable){
        return null;
    }

    public Page<UserLike> findByLikedPostIdAndStatus(String likedUserId, int a, Pageable pageable){
        return null;
    }

    public UserLike findByLikedUserIdAndLikedPostId(String a ,String b){
        return null;
    }
}
