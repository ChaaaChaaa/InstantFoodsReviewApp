package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.Posts;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.List;

public class PostsRecyclerAdapter extends PagedListAdapter<Posts, RecyclerView.ViewHolder> {
    private static final int LAYOUT_DETAIL_PRODUCT = 0;
    private static final int LAYOUT_POSTS = 1;
    private static final int LAYOUT_LOADING = 2;
    private Context context;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private static String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";

    private static final String TAG = PostsRecyclerAdapter.class.getSimpleName();
    private Product productItem;
    private Posts postsItem;
    private View.OnClickListener onClickListener;
    private List<Product> pickProduct;
    private FloatingActionButton floatingActionButton;

    public PostsRecyclerAdapter(List<Product> pickProduct) {
        super(DIFF_CALLBACK);
        this.pickProduct = pickProduct;
    }

    @Override
    public int getItemViewType(int position) {
        int size = getItemCount();
        if (size == 0) {
            return -1;
        } else {
            if (position == 0) {
                return LAYOUT_DETAIL_PRODUCT;
            } else if (getItemCount() > position) {
                return LAYOUT_POSTS;
            } else {
                return LAYOUT_LOADING;
            }
        }
    }


    @Nullable
    @Override
    protected Posts getItem(int position) {
        Log.e("666", "post getItem(position) : " + position);
        return super.getItem(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder recyclerViewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case -1:
                break;
            case LAYOUT_DETAIL_PRODUCT:
                View viewProduct = layoutInflater.inflate(R.layout.item_detail_product, parent, false);
                recyclerViewHolder = new DetailProductViewHolder(viewProduct);
                break;

            case LAYOUT_LOADING:
                View viewLoading = layoutInflater.inflate(R.layout.item_progress, parent, false);
                recyclerViewHolder = new LoadingViewHolder(viewLoading);
                break;

            case LAYOUT_POSTS:
                View viewPosts = layoutInflater.inflate(R.layout.item_post, parent, false);
                recyclerViewHolder = new PostsViewHolder(viewPosts);
                break;
        }
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.e("BindViewPosition ", "" + position + " Size Item :" + getItemCount());
        productItem = pickProduct.get(0);
        Posts posts = getItem(position);

        switch (getItemViewType(position)) {
            case LAYOUT_DETAIL_PRODUCT:
                if (productItem != null) {

                    DetailProductViewHolder detailProductViewHolder = (DetailProductViewHolder) holder;
                    detailProductViewHolder.detailProductName.setText(productItem.getPrTitle());
                    detailProductViewHolder.detailProductRating.setText(Integer.toString(productItem.getPrScore()));
                    String productImageUri = IMG_BASE_URL + productItem.getPrImage();
                    Glide.with(holder.itemView)
                            .load(productImageUri)
                            .override(100,100)
                            .into(detailProductViewHolder.detailProductImage);

                } else {
                    Log.d("TAG", "productItem 's null");
                }

                break;

            case LAYOUT_POSTS:
                if (posts != null) {
                    PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
                    postsViewHolder.postTitle.setText(posts.getTitle());
                    postsViewHolder.postGoodPoint.setText(posts.getGoodContents());
                    postsViewHolder.postBadPoint.setText(posts.getBadContents());
                    postsViewHolder.postRating.setText(Integer.toString(posts.getScore()));

                    String postsImageUri = IMG_BASE_URL + posts.getStoredPath();
                    Glide.with(holder.itemView)
                            .load(postsImageUri)
                            .override(100,100)
                            .into(postsViewHolder.postPicture);
                } else {
                    Log.d("TAG", "post item's null");

                }

                break;

            case LAYOUT_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                if (retryPageLoad) {
                    loadingViewHolder.errorLayout.setVisibility(View.VISIBLE);
                    loadingViewHolder.progressBar.setVisibility(View.GONE);
                    loadingViewHolder.errorTxt.setText(errorMsg != null ? errorMsg : context.getString(R.string.error_msg_unknown));
                } else {
                    loadingViewHolder.errorLayout.setVisibility(View.GONE);
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private static DiffUtil.ItemCallback<Posts> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Posts>() {
                @Override
                public boolean areItemsTheSame(@NonNull Posts oldItem, @NonNull Posts newItem) {
                    return oldItem.getPrId() == newItem.getPrId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Posts oldItem, @NonNull Posts newItem) {
                    return oldItem.equals(newItem);
                }
            };


    public static class DetailProductViewHolder extends RecyclerView.ViewHolder {
        private TextView detailProductRating;
        private TextView detailProductName;
        private ImageView detailProductImage;

        public DetailProductViewHolder(@NonNull View itemView) {
            super(itemView);
            initDetailProductViewHolder();
        }

        private void initDetailProductViewHolder() {
            detailProductImage = itemView.findViewById(R.id.iv_detail_product_image);
            detailProductName = itemView.findViewById(R.id.tv_detail_product_name);
            detailProductRating = itemView.findViewById(R.id.tv_detail_product_rating);
        }
    }

    protected class LoadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar progressBar;
        private ImageButton retryBtn;
        private TextView errorTxt;
        private ConstraintLayout errorLayout;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.loadmore_progress);
            retryBtn = itemView.findViewById(R.id.loadmore_retry);
            errorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            errorLayout = itemView.findViewById(R.id.error_layout);

            retryBtn.setOnClickListener(this);
            errorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    break;
            }
        }
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class PostsViewHolder extends RecyclerView.ViewHolder {

        public ImageView postPicture;
        public TextView postTitle;
        public TextView postRating;
        public TextView postGoodPoint;
        public TextView postBadPoint;
       // public FloatingActionButton floatingActionButton;


        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            initPostsViewHolder();
            //floatingActionButton.setOnClickListener(this);
        }

        private void initPostsViewHolder() {
            postTitle = itemView.findViewById(R.id.tv_post_title);
            postPicture = itemView.findViewById(R.id.iv_post_picture);
            postGoodPoint = itemView.findViewById(R.id.tv_post_good_point);
            postBadPoint = itemView.findViewById(R.id.tv_post_bad_point);
            postRating = itemView.findViewById(R.id.tv_post_rating);
            //floatingActionButton = itemView.findViewById(R.id.btn_post_floating);
        }

//        @Override
//        public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//        }
    }

}
