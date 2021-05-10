package com.myapp.instantfoodsreviewapp.adapter;


import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.paging.PageKeyedDataSource;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private static final String SORT_TYPE = "updated_time";

    String userToken = "";
    int productId;
    private long currentTimeStamp;
    SwipeRefreshLayout swipeRefreshLayout;



    PostDataSource(int productId) {
        Log.e("3 productId", " " + productId);
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
        currentTimeStamp = getTimeStamp();
        Log.e("0 currentTimeStamp", " " + currentTimeStamp);
        Call<PostsResponse> postsResponseCall =
                retrofitInterface.searchProduct(userToken, productId, SORT_TYPE, FIRST_PAGE, currentTimeStamp, PAGE_SIZE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();

                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    if (response.isSuccessful() && postsResponse.getResultData().size() != 0) {
                        List<Posts> responseItems = new ArrayList<>();
                        responseItems.add(null);
                        responseItems.addAll(postsResponse.getResultData());

                        Log.e("00 current page :", " " + FIRST_PAGE);
                        // swipeRefreshLayout.setRefreshing(false);
                        callback.onResult(responseItems, null, FIRST_PAGE + 1);
                    } else {
                        List<Posts> responseItems = new ArrayList<>();
                        responseItems.add(null);
                        //Log.e("Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }

        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        currentTimeStamp = getTimeStamp();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<PostsResponse> postsResponseCall =
                retrofitInterface.searchProduct(userToken, productId, SORT_TYPE, params.key, currentTimeStamp, PAGE_SIZE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (response.isSuccessful()&&  postsResponse.getResultData().size() != 0) {
                        List<Posts> responseItems = new ArrayList<>();
                        responseItems.add(null);
                        responseItems.addAll(postsResponse.getResultData());

                        // Integer key = afterPageKey(params);
                        // Log.e("22 current page :"," "+key);
                        Integer key;

                        if (response.body() == null) {
                            key = null;
                        } else {
                            key = params.key + 1;
                        }

                        callback.onResult(responseItems, key);


                    }
                 else if( postsResponse.getResultData().size() == 0) {
                    List<Posts> responseItems = new ArrayList<>();
                    responseItems.add(null);
                    //Log.e("Server Error", " " + postsResponse.getResultData());
                }
                    else {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Log.e("Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<PostsResponse> postsResponseCall =
                retrofitInterface.searchProduct(userToken, productId, SORT_TYPE, params.key, currentTimeStamp, PAGE_SIZE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (response.isSuccessful()&&  postsResponse.getResultData().size() != 0) {
                        List<Posts> responseItems = new ArrayList<>();
                        responseItems.add(null);
                        responseItems.addAll(postsResponse.getResultData());

                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        Log.e("11 current page :", " " + adjacentKey);
                        callback.onResult(responseItems, adjacentKey);

                    }

                    else if(postsResponse.getResultData().size() != 0) {
                        List<Posts> responseItems = new ArrayList<>();
                        responseItems.add(null);
                    }
                    else {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Log.e("111 Server Error", " " + postsResponse.getResultData());
                    }

                } catch (JsonIOException e) {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
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
