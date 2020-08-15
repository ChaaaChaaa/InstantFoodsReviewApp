package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.fragment.PostsListFragment;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomRecyclerAdapter extends PagedListAdapter<Product, CustomRecyclerAdapter.RecyclerViewHolder>
        implements Filterable{

    private static final String LOG_TAG = CustomRecyclerAdapter.class.getSimpleName();
    private String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";

    private List<ListItem> listItems;
    private List<ListItem> listItemsFull;
    private FoodCategoryList foodCategoryList;
    private Context context;
    private Product product;
    private OnPostsListener onPostsListener;
    private List<PostMultipleItemTypeInterface> data;

    public CustomRecyclerAdapter() {
        super(DIFF_CALLBACK);
       // this.onPostsListener = onPostsListener;
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view,onPostsListener);
    }

    @Nullable
    @Override
    protected Product getItem(int position) {
        Log.e("555","getItem(position) :"+position);
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Product product = getItem(position);
       // Product product = (Product) data.get(position);

        if (product != null) {

            String productImageUri = IMG_BASE_URL + product.getPrImage();
            Glide.with(holder.itemView)
                    .load(productImageUri)
                    .into(holder.imageFood);

            String categoryName = setCategoryResult(product.getPrCategory());
            holder.foodCategory.setText(categoryName);
            holder.foodName.setText(product.getPrTitle());
            holder.productRating.setText(Integer.toString(product.getPrScore()));
            holder.productReviewCount.setText(Integer.toString(product.getPrReviewCount()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = product.getPrId();
                    Toast.makeText(v.getContext(), "position = " + position, Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    PostsListFragment postsListFragment = new PostsListFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postsListFragment).addToBackStack(null).commit();
                    Bundle args = new Bundle();
                    args.putInt("productID", product.getPrId());
                    postsListFragment.setArguments(args);


//                   PostsListFragment postsListFragment = new PostsListFragment();
//                    Bundle args = new Bundle();
//                    args.putString("data", "This data has sent to FragmentTwo");
//                    postsListFragment.setArguments(args);
//                    FragmentTransaction transaction = fragment.getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container,postsListFragment);
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
                }
            });


        } else {
            Log.e("item is null", " " + holder.itemView);
            // Toast.makeText(listItems,"item is null",Toast.LENGTH_LONG).show();
        }
    }




    private String setCategoryResult(int category) {
        String convertCategory = "";
        switch (category) {
            case 1:
                convertCategory = "볶음밥/컵밥";
                break;
            case 2:
                convertCategory = "떡볶이";
                break;

            case 3:
                convertCategory = "라면/즉석 면류";
                break;

            case 4:
                convertCategory = "만두/돈까스/치킨/튀김";
                break;

            case 5:
                convertCategory = "피자/핫도그";
                break;

            case 6:
                convertCategory = "찌개/죽/스프";
                break;
        }
        return convertCategory;
    }


    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    // return oldItem.getPrId().equals(newItem.getPrId());
                    return oldItem.getPrId() == newItem.getPrId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    return oldItem.equals(newItem);
                }
            };



    public class RecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public ImageView imageFood;
        public TextView foodCategory;
        public TextView foodName;
        public TextView reviewContent;
        public TextView productRating;
        public TextView productReviewCount;
        private OnPostsListener onPostsListener;


        public RecyclerViewHolder(@NonNull View itemView,OnPostsListener onPostsListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.onPostsListener = onPostsListener;
            init();
        }

        private void init() {
            imageFood = itemView.findViewById(R.id.image_food);
            foodCategory = itemView.findViewById(R.id.tv_food_category);
            foodName = itemView.findViewById(R.id.tv_food_name);
            reviewContent = itemView.findViewById(R.id.tv_review_content);
            productRating = itemView.findViewById(R.id.tv_product_rating);
            productReviewCount = itemView.findViewById(R.id.tv_food_review_count);
        }

        @Override
       public void onClick(View v) {
           int position = getAdapterPosition();
           Toast.makeText(v.getContext(), "position = " + position, Toast.LENGTH_SHORT).show();
           onPostsListener.onPostsClick(getAdapterPosition());
        }
    }

    public interface OnPostsListener{
        void onPostsClick(int position);
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
