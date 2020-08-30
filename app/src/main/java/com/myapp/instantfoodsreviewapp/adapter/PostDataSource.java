package com.myapp.instantfoodsreviewapp.adapter;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.JsonIOException;
import com.myapp.instantfoodsreviewapp.model.Posts;
import com.myapp.instantfoodsreviewapp.model.PostsResponse;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDataSource extends PageKeyedDataSource<Integer, Posts> {
    private static final String TAG = "PostDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    public ArrayList<Posts> postsList = new ArrayList<>();
    String userToken = "";
    private long currentTimeStamp;

    private String getTokenResult() {
        UserPreference userPreference = new UserPreference();
        userToken = userPreference.getInstance().getString(Config.KEY_TOKEN);
        return userToken;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


    private Long getTimeStamp() {
       // long timestamp;
        Date currentTime = Calendar.getInstance().getTime();
        long timestamp = currentTime.getTime() / 1000L;
       // String result = Long.toString(output);
       // timestamp = Long.parseLong(result) * 1000;
        Log.e("timestamp 1"," "+timestamp);
        return timestamp;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Posts> callback) {
        userToken = getTokenResult();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();

        currentTimeStamp = getTimeStamp();
        Log.e("timestamp 2"," "+currentTimeStamp);
        Call<PostsResponse> postsResponseCall = retrofitInterface.posts(userToken, PAGE_SIZE, currentTimeStamp, FIRST_PAGE);
        postsResponseCall.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                try {
                    PostsResponse postsResponse = response.body();
                    if (response.isSuccessful()) {

                        int postListSize = response.body().getResultData().size();
                        for (int i = 0; i < postListSize; i++) {
                            //Log.e("111 postListSize", " " + i);
                            String postTitle = response.body().getResultData().get(i).getTitle();
                            int postRating = response.body().getResultData().get(i).getScore();
                            String goodPostPoint = response.body().getResultData().get(i).getGoodContents();
                            String badPostPoint = response.body().getResultData().get(i).getBadContents();
                            String postPicture = response.body().getResultData().get(i).getStoredPath();
                            postsList.add(new Posts(postTitle, postRating, goodPostPoint, badPostPoint, postPicture));
                        }


                        for (int i = 0; i < postListSize; i++) {
                            //Log.e("111 postListSize", " " + i);
                            String postTitle = response.body().getResultData().get(i).getTitle();
                            int postRating = response.body().getResultData().get(i).getScore();
                            String goodPostPoint = response.body().getResultData().get(i).getGoodContents();
                            String badPostPoint = response.body().getResultData().get(i).getBadContents();
                            String postPicture = response.body().getResultData().get(i).getStoredPath();
                            postsList.add(new Posts(postTitle, postRating, goodPostPoint, badPostPoint, postPicture));
                        }

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
                        //ArrayList<Product> productList = new ArrayList<>();
                        // PagedList<Product> productList = new PagedList<Product>();
                        List<Posts> responseItems = postsResponse.getResultData();
                        Integer key = beforePageKey(params);
                        int postListSize = response.body().getResultData().size();

                        for (int i = key; i < postListSize; i++) {
                            //Log.e("111 postListSize", " " + i);
                            String postTitle = response.body().getResultData().get(i).getTitle();
                            int postRating = response.body().getResultData().get(i).getScore();
                            String goodPostPoint = response.body().getResultData().get(i).getGoodContents();
                            String badPostPoint = response.body().getResultData().get(i).getBadContents();
                            String postPicture = response.body().getResultData().get(i).getStoredPath();
                            postsList.add(new Posts(postTitle, postRating, goodPostPoint, badPostPoint, postPicture));
                        }

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

                        int postListSize = response.body().getResultData().size();

                        for (int i = key; i < postListSize; i++) {
                            String postTitle = response.body().getResultData().get(i).getTitle();
                            int postRating = response.body().getResultData().get(i).getScore();
                            String goodPostPoint = response.body().getResultData().get(i).getGoodContents();
                            String badPostPoint = response.body().getResultData().get(i).getBadContents();
                            String postPicture = response.body().getResultData().get(i).getStoredPath();
                            postsList.add(new Posts(postTitle, postRating, goodPostPoint, badPostPoint, postPicture));
                        }

                        // Log.e("111 postListSize", " " + postsList.size()); //10

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
