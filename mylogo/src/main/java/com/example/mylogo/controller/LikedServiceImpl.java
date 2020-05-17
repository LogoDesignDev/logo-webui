package com.example.mylogo.controller;

import com.example.mylogo.dao.UserService;
import com.example.mylogo.extra.LikedService;
import com.example.mylogo.extra.UserLike;
import com.example.mylogo.extra.LikedStatusEnum;
import com.example.mylogo.dao.LikesTemplate;
import com.example.mylogo.dao.UserLikeRepository;
import com.example.mylogo.extra.UserInfo;
import com.example.mylogo.extra.LikedCountDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *数据库操作实现类
 */
@Service
@Slf4j
public class LikedServiceImpl implements LikedService {
    @Autowired
    UserLikeRepository likeRepository;

    @Autowired
    LikesTemplate redisService;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserLike save(UserLike userLike) {
        return likeRepository.save(userLike);
    }

    @Override
    @Transactional
    public List<UserLike> saveAll(List<UserLike> list) {
        return likeRepository.saveAll(list);
    }

    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return likeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        return likeRepository.findByLikedPostIdAndStatus(likedPostId, LikedStatusEnum.LIKE.getCode(), pageable);
    }

    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return likeRepository.findByLikedUserIdAndLikedPostId(likedUserId, likedPostId);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLike> list = redisService.getLikedDataFromRedis();
        for (UserLike like : list) {
            UserLike ul = getByLikedUserIdAndLikedPostId(like.getLikedUserId(), like.getLikedPostId());
            if (ul == null){
                //没有记录，直接存入
                save(like);
            }else{
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                save(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {
            UserInfo user = userService.findById(dto.getId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null){
                Integer likeNum = user.getLikeNum() + dto.getCount();
                user.setLikeNum(likeNum);
                //更新点赞数量
                userService.updateInfo(user);
            }
        }
    }
}
