package com.example.mylogo.extra;

import com.example.mylogo.entity.LogoMeta;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationTest {

    @Test
    void TestrRecommWorks() {
        // 0.7, 0.4, 0.5, 0.5, 0.7, 0.6
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            LogoMeta lm1 = new LogoMeta(new ObjectId(sdf.parse("2017-01-01")));
            LogoMeta lm2 = new LogoMeta(new ObjectId(sdf.parse("2018-01-01")));
            LogoMeta lm3 = new LogoMeta(new ObjectId(sdf.parse("2019-01-01")));
            lm2.meta.set(2, 0.9); //  0.7, 0.4, 0.9, 0.5, 0.7, 0.6
            lm3.meta.set(1, 0.6); //  0.6, 0.4, 0.5, 0.5, 0.7, 0.6
            // CosineSim: lm3 < lm2
            ArrayList<LogoMeta> case1 = new ArrayList<>(Arrays.asList(
                    lm1, lm2, lm3
            ));

            List<ObjectId> res = Recommendation.recommWorks(lm1, case1);
            assert(2 == res.size());
            assert(lm3.logoId == res.get(0));
            assert(lm2.logoId == res.get(1));
        }catch (Exception e){
            e.printStackTrace();
            assert(false);
        }

    }

}