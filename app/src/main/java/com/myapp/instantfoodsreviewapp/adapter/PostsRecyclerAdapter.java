package com.myapp.instantfoodsreviewapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

public class PostsRecyclerAdapter extends PagedListAdapter<Post, RecyclerView.ViewHolder> {
    private static final int LAYOUT_DETAIL_PRODUCT = 0;
    private static final int LAYOUT_POSTS = 1;
    private static final int LAYOUT_LOADING = 2;
    private boolean retryPageLoad = false;
    private String errorMsg;
    private Context context;
    private String IMG_BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/ppizil.app.review/";

    public PostsRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder recyclerViewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
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


        switch (getItemViewType(position)) {
            case LAYOUT_DETAIL_PRODUCT:
                Product product = getItem(position); //product 선언을 위해 Post 클래스에 상속했는데 맞는것인지 모르겠음
                DetailProductViewHolder detailProductViewHolder = (DetailProductViewHolder) holder;
                detailProductViewHolder.detailProductName.setText(product.getPrTitle());
                detailProductViewHolder.detailProductRating.setText(Integer.toString(product.getPrScore()));
                loadingPicture(product.getPrImage()).into(detailProductViewHolder.detailProductImage);
                break;

            case LAYOUT_POSTS:
                Post post = getItem(position);
                PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
                postsViewHolder.postGoodPoint.setText(post.getGoodContents());
                postsViewHolder.postBadPoint.setText(post.getBadContents());
                loadingPicture(post.getPrImage()).into(postsViewHolder.postPicture);
                break;

            case LAYOUT_LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
                if(retryPageLoad){
                    loadingViewHolder.errorLayout.setVisibility(View.VISIBLE);
                    loadingViewHolder.progressBar.setVisibility(View.GONE);

                    loadingViewHolder.errorTxt.setText(errorMsg != null ? errorMsg : context.getString(R.string.error_msg_unknown));
                }
                else{
                    loadingViewHolder.errorLayout.setVisibility(View.GONE);
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private RequestBuilder<Drawable> loadingPicture(@NonNull String picturePath){
        return Glide
                .with(context)
                .load(IMG_BASE_URL+picturePath);

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


    protected class DetailProductViewHolder extends RecyclerView.ViewHolder {
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

        private void initPostsViewHolder() {
            postPicture = itemView.findViewById(R.id.iv_post_picture);
            postGoodPoint = itemView.findViewById(R.id.tv_post_good_point);
            postBadPoint = itemView.findViewById(R.id.tv_post_bad_point);
        }
    }

}
