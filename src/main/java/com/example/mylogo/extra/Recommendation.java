package com.example.mylogo.extra;

import com.example.mylogo.entity.Logo;
import com.example.mylogo.entity.LogoMeta;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.util.Pair;

import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;


public class Recommendation {

    static Integer MAX_LOGO = 8;
    static Integer MAX_USER = 9;
    /*
    TODO
    根据参数推荐相似的作品。
    输入被推荐图片的指标， 返回<=8张图片
     */
    public static List<ObjectId> recommWorks(LogoMeta base, List<LogoMeta> allMeta){
        ArrayList<ObjectId> res = new ArrayList<>();
        // 获取所有 Meta数据
        // 计算, 选出前面 <= 20个
        ArrayList<LogoMeta> recomList = Recommendation.CosineSim(base, allMeta);
        // 转换为 ObjectId
        int n = recomList.size();
        for (int i = 0; i < n; i ++){
            res.add(recomList.get(i).logoId);
        }

        return res;
    }

    /*
    TODO
    用户推荐
    功能：输入用户id, 根据该用户的特征返回一系列用户ID。
     */
    public static List<ObjectId> recomUsers(ObjectId userId, List<ObjectId> allUsers){
        ArrayList<ObjectId> res = new ArrayList<>();
        int n = allUsers.size();
        // simple recommandation for testing.
        for(int i = 0;  i < n && i < Recommendation.MAX_USER; i++){
            res.add(allUsers.get(i));
        }

        return res;
    }

    // 余弦相似度
    public static ArrayList<LogoMeta> CosineSim(LogoMeta base, List<LogoMeta> allMeta){
        ArrayList<LogoMeta> res = new ArrayList<>();
        ArrayList<Pair<Double, Integer>> pairs = new ArrayList<Pair<Double, Integer>>();

        int n = allMeta.size();
        int length = base.meta.size();
        for (int i = 0; i < n; i++){
            LogoMeta other = allMeta.get(i);
            if (other.logoId == base.logoId){ // 排除自己
                continue;
            }

            Double a = 0.0;
            Double lo = 0.0;
            Double lb = 0.0;
            for (int j = 0; j < length; j++) {
                a += other.meta.get(j) * base.meta.get(j);
                lo += Math.pow(other.meta.get(j), 2);
                lb += Math.pow(base.meta.get(j), 2);
            }
            if (lo * lb == 0.0){
                pairs.add(Pair.of(0.0, i));
            }else{
                Double p = a / Math.sqrt(lo * lb);
                if (p > 1.0){
                    p = 1.0;
                }else if (p < -1.0){
                    p = -1.0;
                }
                pairs.add(Pair.of(p, i));
            }
        }
        pairs.sort(Comparator.comparing(Pair::getFirst));
        Collections.reverse(pairs);
        // 排除了自身，所以是 n - 1
        for (int i = 0; i < Recommendation.MAX_LOGO && i < n - 1; i++){
            res.add(
                    allMeta.get(pairs.get(i).getSecond())
            );
        }
        return res;
    }

    // 协同过滤，User推进
    public static ArrayList<LogoMeta> CollaborativeFiltering(LogoMeta base, List<LogoMeta> allMeta){
        ArrayList<LogoMeta> res = new ArrayList<>();
        // unimplemented.
        return res;
    }

    // 对应API: 获取推荐作品
    public static List<ObjectId> HomePageRecomLogo(List<LogoMeta> allMeta){
        ArrayList<ObjectId> res = new ArrayList<>();
        for (int i = 0; i < MAX_USER; i++){
            res.add(allMeta.get(i).logoId);
        }
        return res;
    }

    // 对应API: 获取设计师推荐
    public static List<ObjectId> HomePageRecomUser(List<ObjectId> allUsers){
        ArrayList<ObjectId> res = new ArrayList<>();
        int n = allUsers.size();
        // simple recommandation for testing.
        for(int i = 0;  i < n && i < Recommendation.MAX_USER; i++){
            res.add(allUsers.get(i));
        }

        return res;
    }
}

















