package com.myapp.instantfoodsreviewapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.RecyclerViewHolder>
       implements Filterable  {

    private static final String LOG_TAG = CustomRecyclerAdapter.class.getSimpleName();

    private List<ListItem> listItems;
    private List<ListItem> listItemsFull;
    private FoodCategoryList foodCategoryList;
    private Context context;

    public CustomRecyclerAdapter(Context context, List<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
        listItemsFull = new ArrayList<>(listItems); //독립적으로 사용하기위해 listItems를 복사
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.imageFood.setImageResource(listItems.get(position).getImageFood());
        holder.foodCategory.setText(listItems.get(position).getFoodCategory());
        holder.foodName.setText(listItems.get(position).getFoodName());
        holder.reviewContent.setText(listItems.get(position).getReviewContent());
        holder.imageStar1.setImageResource(listItems.get(position).getImageStar1());
        holder.imageStar2.setImageResource(listItems.get(position).getImageStar2());
        holder.imageStar3.setImageResource(listItems.get(position).getImageStar3());
    }

    @Override
    public int getItemCount() {
        if (listItems != null) {
            return listItems.size();
        }
        return 0;
    }

//    @Override
//    public Filter getFilter() {
//        return null;
//    }



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
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }


    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ListItem> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(listItemsFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ListItem item : listItemsFull){
                    if(item.getFoodName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listItems.clear();
            listItems.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
