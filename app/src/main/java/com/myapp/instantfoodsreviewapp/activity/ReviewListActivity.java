package com.myapp.instantfoodsreviewapp.activity;

import androidx.fragment.app.Fragment;

import com.myapp.instantfoodsreviewapp.fragment.ReviewListFragment;
import com.myapp.instantfoodsreviewapp.fragment.SingleFragmentActivity;

public class ReviewListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ReviewListFragment();
    }
}
