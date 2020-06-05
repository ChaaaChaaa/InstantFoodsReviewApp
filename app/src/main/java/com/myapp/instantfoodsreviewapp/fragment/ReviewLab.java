package com.myapp.instantfoodsreviewapp.fragment;

import android.content.Context;

import com.myapp.instantfoodsreviewapp.model.ReviewData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewLab {
    private static ReviewLab reviewLab;
    private List<ReviewData> reviewsList;

    public static ReviewLab get(Context context) {
        if (reviewLab == null) {
            reviewLab = new ReviewLab(context);
        }
        return reviewLab;
    }

    private ReviewLab(Context context) {
        reviewsList = new ArrayList<>();
        for(int i=0; i<100; i++){
            ReviewData reviewData = new ReviewData();
            reviewData.setGoodPointReview("좋아요#"+i);
            reviewsList.add(reviewData);
        }
    }

    public List<ReviewData> getReviews() {
        return reviewsList;
    }

    public ReviewData getReview(UUID id) {
        for (ReviewData reviewData : reviewsList) {
            if (reviewData.getId().equals(id)) {
                return reviewData;
            }
        }
        return null;
    }
}
