package com.myapp.instantfoodsreviewapp.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.myapp.instantfoodsreviewapp.fragment.ReviewFragment;
import com.myapp.instantfoodsreviewapp.fragment.SingleFragmentActivity;

public class ReviewActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ReviewFragment();
    }
}
