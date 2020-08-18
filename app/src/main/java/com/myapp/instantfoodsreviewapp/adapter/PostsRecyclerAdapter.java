package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.RequestBuilder;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.Post;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class PostsRecyclerAdapter extends PagedListAdapter<Post, RecyclerView.ViewHolder> {
    private static final int LAYOUT_DETAIL_PRODUCT = 0;
    private static final int LAYOUT_POSTS = 1;
    private static final int LAYOUT_LOADING = 2;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private static String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";

    private static final String TAG = PostsRecyclerAdapter.class.getSimpleName();
    private Product productItem;
    private Post postItem;
    private View.OnClickListener onClickListener;
    private List<Product> pickProduct;

//
//    public PostsRecyclerAdapter() {
//        super(context);
//    }

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


//    @Override
//    public int getItemViewType(int position) {
//        if (position > products.size() - 1) {
//            return 2;
//        } else {
//            return 1;
//        }
//    }

    @Nullable
    @Override
    protected Post getItem(int position) {
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
        int viewType = holder.getItemViewType();
        //productItem = products.get(position);
        productItem = pickProduct.get(0);



        switch (getItemViewType(position)) {
            case LAYOUT_DETAIL_PRODUCT:
                // Log.e("data.get(productID)"," "+(Product) data.get(productID));
                // data.get(product.getPrId())
                //Product product = (Product) data.get(position);
//                Product product = (Product) data.get(productID);
//                DetailProductViewHolder detailProductViewHolder = (DetailProductViewHolder) holder;
//                detailProductViewHolder.detailProductName.setText(productItem.getPrTitle());
//                detailProductViewHolder.detailProductRating.setText(Integer.toString(productItem.getPrScore()));
//                ((DetailProductViewHolder) holder).loadPicture(productItem.getPrImage());
//                ((DetailProductViewHolder) holder).detailProductName.setText(productItem.getPrTitle());
//                ((DetailProductViewHolder) holder).detailProductRating.setText(productItem.getPrScore());
//                ((DetailProductViewHolder) holder).loadPicture(productItem.getPrImage());


                if (productItem != null) {
                    DetailProductViewHolder detailProductViewHolder = (DetailProductViewHolder) holder;
                    detailProductViewHolder.detailProductName.setText(productItem.getPrTitle());
                    detailProductViewHolder.detailProductRating.setText(Integer.toString(productItem.getPrScore()));

                    String productImageUri = IMG_BASE_URL + productItem.getPrImage();
                    Glide.with(holder.itemView)
                            .load(productImageUri)
                            .into(detailProductViewHolder.detailProductImage);

                   // detailProductViewHolder.loadPicture(productItem.getPrImage());

                } else {
                    Log.d("8888", "productItem 's null");
                }



                break;

            case LAYOUT_POSTS:
//                Post post = (Post) getItem(position);
//                //Post post = (Post) data.get(productID);
//                PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
//                postsViewHolder.postGoodPoint.setText(post.getGoodContents());
//                postsViewHolder.postBadPoint.setText(post.getBadContents());
//                postsViewHolder.loadImage(post.getStoredPath());

                if (postItem != null) {
//                   ((PostsViewHolder) holder).postGoodPoint.setText(postItem.getGoodContents());
//                   ((PostsViewHolder) holder).postBadPoint.setText(postItem.getBadContents());
//                   ((PostsViewHolder) holder).loadImage(postItem.getStoredPath());
                    Post post = getItem(position);
                    PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
                    postsViewHolder.postGoodPoint.setText(post.getGoodContents());
                    postsViewHolder.postBadPoint.setText(post.getBadContents());
                    postsViewHolder.loadImage(post.getStoredPath());
                } else {
                    Log.d("8888", "post item's null");

                }

                break;

            case LAYOUT_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                if (retryPageLoad) {
                    loadingViewHolder.errorLayout.setVisibility(View.VISIBLE);
                    loadingViewHolder.progressBar.setVisibility(View.GONE);

                    // loadingViewHolder.errorTxt.setText(errorMsg != null ? errorMsg : context.getString(R.string.error_msg_unknown));
                } else {
                    loadingViewHolder.errorLayout.setVisibility(View.GONE);
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private static RequestBuilder<Drawable> loadingPicture(Context context, @NonNull String picturePath) {
        return Glide
                .with(context)
                .load(IMG_BASE_URL + picturePath);
    }


    private static DiffUtil.ItemCallback<Post> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Post>() {
                @Override
                public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                    return oldItem.getPrId() == newItem.getPrId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
                    return oldItem.equals(newItem);
                }
            };


    public static class DetailProductViewHolder extends RecyclerView.ViewHolder {
        private TextView detailProductRating;
        private TextView detailProductName;
        private ImageView detailProductImage;
        private View itemview = null;

        public DetailProductViewHolder(@NonNull View itemView) {
            super(itemView);
            initDetailProductViewHolder();
        }

        private void initDetailProductViewHolder() {
            detailProductImage = itemView.findViewById(R.id.iv_detail_product_image);
            detailProductName = itemView.findViewById(R.id.tv_detail_product_name);
            detailProductRating = itemView.findViewById(R.id.tv_detail_product_rating);
        }

        public void loadPicture(String path) {
            loadingPicture(itemView.getContext(), path);

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
        //notifyItemChanged(movieResults.size() - 1);
        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class PostsViewHolder extends RecyclerView.ViewHolder {

        public ImageView postPicture;
        public TextView postGoodPoint;
        public TextView postBadPoint;


        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            initPostsViewHolder();
        }

        public void loadImage(String path) {
            loadingPicture(itemView.getContext(), path);

        }

        private void initPostsViewHolder() {
            postPicture = itemView.findViewById(R.id.iv_post_picture);
            postGoodPoint = itemView.findViewById(R.id.tv_post_good_point);
            postBadPoint = itemView.findViewById(R.id.tv_post_bad_point);
        }
    }

}
