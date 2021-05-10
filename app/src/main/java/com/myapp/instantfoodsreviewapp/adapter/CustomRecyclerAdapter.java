package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.fragment.PostsListFragment;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends PagedListAdapter<Product, CustomRecyclerAdapter.RecyclerViewHolder> {

    private static final String LOG_TAG = CustomRecyclerAdapter.class.getSimpleName();
    private String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";


    public CustomRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
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

    @Nullable
    @Override
    protected Product getItem(int position) {
        Log.e("666", "Product getItem(position) : " + position);
        return super.getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Product product = getItem(position);
        Log.e("Product BindPosition ", "" + position + " Size Item :" + getItemCount());
        Log.e("PrReviewCount()", "" + product.getPrReviewCount() + " product.getPrReviewCount()" + product.getPrReviewCount());
        if (product != null) {
            String productImageUri = IMG_BASE_URL + product.getPrImage();
            Glide.with(holder.itemView)
                    .load(productImageUri)
                    .into(holder.imageFood);

            String categoryName = setCategoryResult(product.getPrCategory());
            holder.foodCategory.setText(categoryName);
            holder.foodName.setText(product.getPrTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Product> pickProduct = new ArrayList<>();
                    pickProduct.add(getItem(position));
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    PostsListFragment postsListFragment = new PostsListFragment(pickProduct);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, postsListFragment)
                            .addToBackStack(null)
                            .commit();
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


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageFood;
        public TextView foodCategory;
        public TextView foodName;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            imageFood = itemView.findViewById(R.id.image_food);
            foodCategory = itemView.findViewById(R.id.tv_food_category);
            foodName = itemView.findViewById(R.id.tv_food_name);
        }
    }
}
