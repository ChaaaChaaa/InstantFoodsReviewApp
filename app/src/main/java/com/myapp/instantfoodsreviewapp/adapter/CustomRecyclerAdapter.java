package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomRecyclerAdapter extends PagedListAdapter<Product,CustomRecyclerAdapter.RecyclerViewHolder>
        implements Filterable {

    private static final String LOG_TAG = CustomRecyclerAdapter.class.getSimpleName();

    private List<ListItem> listItems;
    private List<ListItem> listItemsFull;
    private FoodCategoryList foodCategoryList;
    private Context context;

//    public CustomRecyclerAdapter(Context context, List<ListItem> listItems) {
//        super(DIFF_CALLBACK);
//        this.context = context;
//        this.listItems = listItems;
//        listItemsFull = new ArrayList<>(listItems); //독립적으로 사용하기위해 listItems를 복사
//    }

    public CustomRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.item, parent, false);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.imageFood.setImageResource(listItems.get(position).getImageFood());
        holder.foodCategory.setText(listItems.get(position).getFoodCategory());
        holder.foodName.setText(listItems.get(position).getFoodName());
        holder.reviewContent.setText(listItems.get(position).getReviewContent());
        holder.productRating.setText(listItems.get(position).getProductRating());
//        holder.imageStar1.setImageResource(listItems.get(position).getImageStar1());
//        holder.imageStar2.setImageResource(listItems.get(position).getImageStar2());
//        holder.imageStar3.setImageResource(listItems.get(position).getImageStar3());
    }

    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getPrId().equals(newItem.getPrId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };






//    @Override
//    public int getItemCount() {
//        if (listItems != null) {
//            return listItems.size();
//        }
//        return 0;
//    }

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
        public TextView productRating;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            imageFood = itemView.findViewById(R.id.image_food);
            foodCategory = itemView.findViewById(R.id.tv_food_category);
            foodName = itemView.findViewById(R.id.tv_food_name);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
            productRating = itemView.findViewById(R.id.tv_product_rating);
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
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listItemsFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ListItem item : listItemsFull) {
                    if (item.getFoodName().toLowerCase().contains(filterPattern)) {
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
