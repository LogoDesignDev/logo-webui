package com.example.mylogo.dao;

import java.util.List;
import com.example.mylogo.extra.UserLike;
import com.example.mylogo.extra.LikedCountDTO;

public interface LikesTemplate {
    /**
     * 点赞。状态为1
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的点赞数加1
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 该用户的点赞数减1
     */
    void decrementLikedCount(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();
    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

}
