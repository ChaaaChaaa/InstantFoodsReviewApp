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

public class StewAdapter extends RecyclerView.Adapter<StewAdapter.StewViewHolder> {
    private ArrayList<ListItem> stewList;

    public static class StewViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageFood;
        public TextView foodCategory;
        public TextView foodName;
        public TextView reviewContent;
        public ImageView imageStar1;
        public ImageView imageStar2;
        public ImageView imageStar3;

        public StewViewHolder(View stewItemView) {
            super(stewItemView);
            initStew();
        }

        private void initStew() {
            imageFood = itemView.findViewById(R.id.image_food);
            foodCategory = itemView.findViewById(R.id.tv_food_category);
            foodName = itemView.findViewById(R.id.tv_food_name);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
            imageStar1 = itemView.findViewById(R.id.image_star1);
            imageStar2 = itemView.findViewById(R.id.image_star2);
            imageStar3 = itemView.findViewById(R.id.image_star3);
        }
    }

    public StewAdapter(ArrayList<ListItem> stewList){
        this.stewList = stewList;
    }


    @NonNull
    @Override
    public StewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        StewViewHolder stewViewHolder = new StewViewHolder(view);
        return stewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StewViewHolder holder, int position) {
        ListItem currentItem = stewList.get(position);
        holder.imageFood.setImageResource(currentItem.getImageFood());
        holder.foodCategory.setText(currentItem.getFoodCategory());
        holder.foodName.setText(currentItem.getFoodName());
        holder.reviewContent.setText(currentItem.getReviewContent());
        holder.imageStar1.setImageResource(currentItem.getImageStar1());
        holder.imageStar2.setImageResource(currentItem.getImageStar2());
        holder.imageStar3.setImageResource(currentItem.getImageStar3());
    }



    @Override
    public int getItemCount() {
        return stewList.size();
    }

}
