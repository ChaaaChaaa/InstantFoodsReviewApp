package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.JsonIOException;
import com.myapp.instantfoodsreviewapp.model.Post;
import com.myapp.instantfoodsreviewapp.model.PostsResponse;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<Integer, Post> {
    private static final String TAG = "PostDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private int DATE=1594864449;
    public ArrayList<Post> postsList = new ArrayList<>();
    String userToken="";


    private String getTokenResult(){
        UserPreference userPreference = new UserPreference();
        userToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
        return userToken;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Post> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<PostsResponse> postsResponseCall = retrofitInterface.posts(userToken, PAGE_SIZE,DATE, FIRST_PAGE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (response.isSuccessful()) {
                        //ArrayList<Product> productList = new ArrayList<>();
                        // PagedList<Product> productList = new PagedList<Product>();

                        int postListSize = response.body().getResultData().size();
                        Log.e("111 postListSize", " " + postListSize);

                       for(int i=0; i<postListSize; i++){
                           String postTitle = response.body().getResultData().get(0).getTitle();
                           String goodPostPoint = response.body().getResultData().get(0).getGoodContents();
                           String badPostPoint = response.body().getResultData().get(0).getBadContents();
                           String postPicture = response.body().getResultData().get(0).getStoredPath();
                           postsList.add(new Post(postTitle,goodPostPoint,badPostPoint,postPicture));
                       }


                        Log.e("111 postListSize", " " + postsList.size()); //10

                        List<Post> responseItems = postsResponse.getResultData();
                        callback.onResult(responseItems, null, FIRST_PAGE + 1);

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
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Post> callback) {

    }
}
