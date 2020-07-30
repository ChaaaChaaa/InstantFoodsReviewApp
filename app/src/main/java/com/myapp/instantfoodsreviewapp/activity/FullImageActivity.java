package com.myapp.instantfoodsreviewapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.myapp.instantfoodsreviewapp.R;

public class FullImageActivity extends Activity {
    private ImageView fullImageView;
    // private String IMG_BASE_URL = "http://www.ppizil.kro.kr/review/file/";
    private String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";
    private String ORIGINAL_IMAGE_NAME = "originalImage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        init();
        setFullImageView();
    }

    private void init() {
        fullImageView = findViewById(R.id.full_image_view);
    }

    private void setFullImageView() {
        Intent getImageIntent = getIntent();
        String getImagePath = getImageIntent.getStringExtra(ORIGINAL_IMAGE_NAME);
        if (getImagePath != null) {
            Glide.with(this)
                    .load(IMG_BASE_URL + getImagePath)
                    .into(fullImageView);
        }

    }
}
