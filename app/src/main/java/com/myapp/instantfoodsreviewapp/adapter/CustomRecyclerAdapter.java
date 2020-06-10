package com.myapp.instantfoodsreviewapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    private ArrayList<ListItem> listItems;
    private FoodCategoryList foodCategoryList;

    public CustomRecyclerAdapter(ArrayList<ListItem> listItems) {
        this.listItems = listItems;
    }

//    private class StewViewHolder extends RecyclerView.ViewHolder {
//        private ArrayList<ListItem> stewList= new ArrayList<>();
//
//        public ImageView imageFood;
//        public TextView foodCategory;
//        public TextView foodName;
//        public TextView reviewContent;
//        public ImageView imageStar1;
//        public ImageView imageStar2;
//        public ImageView imageStar3;
//
//        public StewViewHolder(View stewItemView) {
//            super(stewItemView);
//            initStew();
//        }
//
//        private void initStew() {
//            imageFood = itemView.findViewById(R.id.image_food);
//            foodCategory = itemView.findViewById(R.id.tv_food_category);
//            foodName = itemView.findViewById(R.id.tv_food_name);
//            reviewContent = itemView.findViewById(R.id.tv_review_content);
//            imageStar1 = itemView.findViewById(R.id.image_star1);
//            imageStar2 = itemView.findViewById(R.id.image_star2);
//            imageStar3 = itemView.findViewById(R.id.image_star3);
//        }
//
//        public void bind(int position) {
//            ListItem currentItem = stewList.get(position);
//            imageFood.setImageResource(currentItem.getImageFood());
//            foodCategory.setText(currentItem.getFoodCategory());
//            foodName.setText(currentItem.getFoodName());
//            reviewContent.setText(currentItem.getReviewContent());
//            imageStar1.setImageResource(currentItem.getImageStar1());
//            imageStar2.setImageResource(currentItem.getImageStar2());
//            imageStar3.setImageResource(currentItem.getImageStar3());
//
//        }
//    }
//
//
    @Override
    public int getItemViewType(int position) {
        ListItem listItem = listItems.get(position);
        if (listItem.getFoodType() == foodCategoryList.STEW) {
            Log.d("one","one");
            return TYPE_ONE;
        } else if (listItem.getFoodType() == foodCategoryList.DDOKBOKKI) {
            Log.d("two","two");
            return TYPE_TWO;
        } else {
            Log.d("test","test");
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == TYPE_ONE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_stew,parent,false);
            return new StewViewHolder(view);
        }

        else if(viewType == TYPE_TWO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_ddokbokki,parent,false);
            return new DdokbokkiViewHolder(view);
        }

        else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        if(listItems.get(position).getFoodCategory() == "찌개"){
//            ((StewViewHolder) holder).bind(position);
//        }
//
//        else if(listItems.get(position).getFoodCategory() == "떡볶이"){
//            ((DdokbokkiViewHolder) holder).bind(position);
//        }

        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((StewViewHolder)holder, position);
                break;
            case TYPE_TWO:
                initLayoutTwo((DdokbokkiViewHolder) holder, position);
                break;
            default:
                break;
        }

    }

    private void initLayoutOne(StewViewHolder holder, int position) {
        holder.imageFood.setImageResource(listItems.get(position).getImageFood());
        holder.foodCategory.setText(listItems.get(position).getFoodCategory());
        holder. foodName.setText(listItems.get(position).getFoodName());
        holder.reviewContent.setText(listItems.get(position).getReviewContent());
        holder.imageStar1.setImageResource(listItems.get(position).getImageStar1());
        holder. imageStar2.setImageResource(listItems.get(position).getImageStar2());
        holder. imageStar3.setImageResource(listItems.get(position).getImageStar3());
    }

    private void initLayoutTwo(DdokbokkiViewHolder holder, int position) {
        holder.imageFood.setImageResource(listItems.get(position).getImageFood());
        holder.foodCategory.setText(listItems.get(position).getFoodCategory());
        holder. foodName.setText(listItems.get(position).getFoodName());
        holder.reviewContent.setText(listItems.get(position).getReviewContent());
        holder.imageStar1.setImageResource(listItems.get(position).getImageStar1());
        holder. imageStar2.setImageResource(listItems.get(position).getImageStar2());
        holder. imageStar3.setImageResource(listItems.get(position).getImageStar3());
    }

    @Override
    public int getItemCount() {
        return  listItems == null ? 0 : listItems.size();
    }
}
