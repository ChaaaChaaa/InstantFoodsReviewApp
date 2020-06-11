package com.myapp.instantfoodsreviewapp.adapter;

import android.app.Activity;
import android.content.Context;
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

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.RecyclerViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final String LOG_TAG = CustomRecyclerAdapter.class.getSimpleName();

    private ArrayList<ListItem> listItems;
    private FoodCategoryList foodCategoryList;
    Context context;

    public CustomRecyclerAdapter(Activity context,ArrayList<ListItem> listItem) {
        this.context = context;
        this.listItems = listItem;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
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
        if (listItems != null) {
            return listItems.size();
        }
        return 0;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        //private ArrayList<ListItem> stewList= new ArrayList<>();

        public ImageView imageFood;
        public TextView foodCategory;
        public TextView foodName;
        public TextView reviewContent;
        public ImageView imageStar1;
        public ImageView imageStar2;
        public ImageView imageStar3;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
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

}
