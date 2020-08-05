package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.JsonIOException;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.model.ProductResponse;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {
    private static final String TAG = "ProductDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private static final int CATEGORY_ID = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, FIRST_PAGE);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
               try
                {
                    if (response.isSuccessful())
                    {
                        callback.onResult(response.body().getResultData(),null,FIRST_PAGE+1);


                    }

                } catch (JsonIOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }

        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, FIRST_PAGE);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try
                {
                    if (response.isSuccessful())
                    {
                        Integer key = (params.key > 1)?params.key -1 : null;
                        callback.onResult(response.body().getResultData(),key);
                    }

                } catch (JsonIOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, FIRST_PAGE);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try
                {
                    if (response.isSuccessful())
                    {
                        Integer key = (params.key > 1)?params.key +1 : null;
                        callback.onResult(response.body().getResultData(),key);
                    }

                } catch (JsonIOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}
