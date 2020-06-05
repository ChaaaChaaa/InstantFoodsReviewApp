package com.myapp.instantfoodsreviewapp.fragment;

import android.content.Context;

public class ReviewSingleTon {
    private static ReviewSingleTon reviewSingleTon;

    private static ReviewSingleTon get(Context context) {
        if (reviewSingleTon == null) {
            reviewSingleTon = new ReviewSingleTon(context);
        }
        return reviewSingleTon;
    }

    private ReviewSingleTon(Context context) {

    }
}
