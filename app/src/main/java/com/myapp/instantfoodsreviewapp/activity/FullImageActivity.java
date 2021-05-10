package com.myapp.instantfoodsreviewapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.ActivityFullImageBinding;

public class FullImageActivity extends Activity {
    private ImageView fullImageView;
    private ActivityFullImageBinding activityFullImageBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFullImageBinding = DataBindingUtil.setContentView(this,R.layout.activity_full_image);
        //setContentView(activityFullImageBinding.fullImageView);
        init();
        setFullImageView();
    }

    private void init() {
        //fullImageView = findViewById(R.id.full_image_view);
        fullImageView = activityFullImageBinding.fullImageView;
    }

    private void setFullImageView() {
        Intent getImageIntent = getIntent();
        String ORIGINAL_IMAGE_NAME = "originalImage";
        String getImagePath = getImageIntent.getStringExtra(ORIGINAL_IMAGE_NAME);
        if (getImagePath != null) {
            // private String IMG_BASE_URL = "http://www.ppizil.kro.kr/review/file/";
            String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";
            Glide.with(this)
                    .load(IMG_BASE_URL + getImagePath)
                    .into(fullImageView);
        }
    }
}
