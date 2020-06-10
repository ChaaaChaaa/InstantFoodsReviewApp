package com.myapp.instantfoodsreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ListItem;

import java.util.ArrayList;

class DdokbokkiViewHolder extends RecyclerView.ViewHolder {
    private ArrayList<ListItem> ddokList;
    private ListItem currentItem;

    public ImageView imageFood;
    public TextView foodCategory;
    public TextView foodName;
    public TextView reviewContent;
    public ImageView imageStar1;
    public ImageView imageStar2;
    public ImageView imageStar3;

    public DdokbokkiViewHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    private void init() {
        imageFood = itemView.findViewById(R.id.image_food);
        foodCategory = itemView.findViewById(R.id.tv_food_category);
        foodName = itemView.findViewById(R.id.tv_food_name);
        reviewContent = itemView.findViewById(R.id.tv_review_content);
        imageStar1 = itemView.findViewById(R.id.image_star1);
        imageStar2 = itemView.findViewById(R.id.image_star2);
        imageStar3 = itemView.findViewById(R.id.image_star3);
    }

    public void bind(int position) {
       currentItem = ddokList.get(position);
        imageFood.setImageResource(currentItem.getImageFood());
        foodCategory.setText(currentItem.getFoodCategory());
        foodName.setText(currentItem.getFoodName());
        reviewContent.setText(currentItem.getReviewContent());
        imageStar1.setImageResource(currentItem.getImageStar1());
        imageStar2.setImageResource(currentItem.getImageStar2());
        imageStar3.setImageResource(currentItem.getImageStar3());
    }
}


