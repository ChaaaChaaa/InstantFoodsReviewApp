package com.myapp.instantfoodsreviewapp.adapter;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.JsonIOException;
import com.myapp.instantfoodsreviewapp.model.Posts;
import com.myapp.instantfoodsreviewapp.model.PostsResponse;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<Integer, Posts> {
    private static final String TAG = "PostDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    public ArrayList<Product> postsList = new ArrayList<>();
    String userToken = "";
    int productId;
    private long currentTimeStamp;
  //  PostsRecyclerAdapter postsRecyclerAdapter = new PostsRecyclerAdapter(postsList);

//    private int test(){
//        productId = postsRecyclerAdapter.setPicKProductId();
//        return productId;
//    }


    PostDataSource(int productId){
        Log.e("3 productId"," "+productId);
        this.productId = productId;
    }

    private String getTokenResult() {
        UserPreference userPreference = new UserPreference();
        userToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
        return userToken;
    }

    private Long getTimeStamp() {
        Date currentTime = Calendar.getInstance().getTime();
        long timestamp = currentTime.getTime() / 1000L;
        return timestamp;
    }




    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();

        //productId = test();
        currentTimeStamp = getTimeStamp();

        Call<PostsResponse> postsResponseCall = retrofitInterface.posts(userToken, PAGE_SIZE, currentTimeStamp, FIRST_PAGE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (response.isSuccessful()) {
                        List<Posts> responseItems = postsResponse.getResultData();
                        callback.onResult(responseItems, null, FIRST_PAGE + 1);

                    } else {
                        Log.e("Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }

        });
    }


    private Integer beforePageKey(LoadParams<Integer> params) {
        Integer key;

        if (params.key > 1) {
            key = params.key - 1;
        } else {
            key = 0;
        }
        return key;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<PostsResponse> postsResponseCall = retrofitInterface.posts(userToken, PAGE_SIZE, currentTimeStamp, params.key);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (response.isSuccessful()) {
                        List<Posts> responseItems = postsResponse.getResultData();
                        Integer key = beforePageKey(params);
                        callback.onResult(responseItems, key);

                    } else {
                        Log.e("111 Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    private Integer afterPageKey(LoadParams<Integer> params) {
        Integer key;

        if (params.key > 1) {
            key = params.key + 1;
        } else {
            key = 0;
        }

        return key;
    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<PostsResponse> postsResponseCall = retrofitInterface.posts(userToken, PAGE_SIZE, currentTimeStamp, params.key);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (response.isSuccessful()) {
                        List<Posts> responseItems = postsResponse.getResultData();
                        Integer key = afterPageKey(params);
                        callback.onResult(responseItems, key);

                    } else {
                        Log.e("Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}
