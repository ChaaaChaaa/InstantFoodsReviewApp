package com.myapp.instantfoodsreviewapp.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.fragment.ProductListFriedRiceFragment;
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.model.ProductResponse;
import com.myapp.instantfoodsreviewapp.model.entity.ProductListDto;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {
    private static final String TAG = "ProductDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private static final int CATEGORY_ID = 1;
    private ArrayList<Product> productList = new ArrayList<>();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();

        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, FIRST_PAGE);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    ProductResponse productResponse = response.body();
                    if (response.isSuccessful()) {
                        //ArrayList<Product> productList = new ArrayList<>();
                        // PagedList<Product> productList = new PagedList<Product>();

                        int productListSize = response.body().getResultData().size();
                        Log.e("111 productListSize", " " + productListSize);

                        for (int i = 0; i < productListSize; i++) {
                            String productPicture = response.body().getResultData().get(i).getPrImage();
                            int productCategory = response.body().getResultData().get(i).getPrCategory();
                            String title = response.body().getResultData().get(i).getPrTitle();
                            Object reviewCount = response.body().getResultData().get(i).getPrReviewCount();
                            int productScore = response.body().getResultData().get(i).getPrScore();
                            productList.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
                        }

                        //productViewModel.setProductPagedList(productList);


//                        Bundle result = new Bundle();
//                        result.putStringArrayList("productList",productList);


                        //CustomRecyclerAdapter customRecyclerAdapter = new CustomRecyclerAdapter(productList);
                        //productListFriedRiceFragment = new ProductListFriedRiceFragment(customRecyclerAdapter);


                        //List<Product> resultData = productResponse.getResultData();
                        //  List<Product> productList = new ArrayList<>();
                        // List<Product> productList = response.body().getResultData();
                        // JsonObject products = productList.get()
                        // JsonObject jsonObject = new JsonObject();
                        //JsonArray jsonArray = jsonObject.getAsJsonArray("result").getAsJsonObject()
                        Log.e("111 ProductList", " " + productList.size()); //10
                        //callback.onResult(productList, null, FIRST_PAGE + 1);
                        // callback.onResult(response.body().getResultData(),null,FIRST_PAGE+1);
                        //callback.onResult(productList);

                        List<Product> responseItems = productResponse.getResultData();
                        callback.onResult(responseItems, null, FIRST_PAGE + 1);

                    } else {
                        Log.e("111 Server Error", " " + productResponse.getResultData());
                    }

                } catch (JsonIOException e) {


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
//        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
//        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, FIRST_PAGE);
//        productResponseCall.enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                try {
//                    ProductResponse productResponse = response.body();
//
//                    if (response.isSuccessful()) {
//                        List<Product> responseItems = productResponse.getResultData();
//                        Integer key = (params.key > 1) ? params.key - 1 : null;
//                        // callback.onResult(response.body().getResultData(), key);
//                        Log.e("222 ProductList2", " " + productList.size()); // 안옴
//
//                        int productListSize = response.body().getResultData().size();
//
//                        for (int i = key; i < productListSize; i++) {
//                            String productPicture = response.body().getResultData().get(i).getPrImage();
//                            int productCategory = response.body().getResultData().get(i).getPrCategory();
//                            String title = response.body().getResultData().get(i).getPrTitle();
//                            Object reviewCount = response.body().getResultData().get(i).getPrReviewCount();
//                            int productScore = response.body().getResultData().get(i).getPrScore();
//                            productList.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
//                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
//                        }
//                        Log.e("222 params.key "," "+params.key);
//                        callback.onResult(responseItems,params.key+1);
//
//                    }
//
//                } catch (JsonIOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//
//            }
//        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(CATEGORY_ID, PAGE_SIZE, params.key);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();

                try {
                    if (response.isSuccessful()) {
                        Integer key = (params.key > 1) ? params.key + 1 : null;
                        // callback.onResult(response.body().getResultData(), key);
                        List<Product> responseItems = productResponse.getResultData();
                        Log.e("222 ProductList3", " " + productList.size());
                        int productListSize = response.body().getResultData().size();

                        for (int i = key; i < productListSize; i++) {
                            String productPicture = response.body().getResultData().get(i).getPrImage();
                            int productCategory = response.body().getResultData().get(i).getPrCategory();
                            String title = response.body().getResultData().get(i).getPrTitle();
                            Object reviewCount = response.body().getResultData().get(i).getPrReviewCount();
                            int productScore = response.body().getResultData().get(i).getPrScore();
                            productList.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
                        }

                        callback.onResult(responseItems, params.key + 1);
                       // Log.e("222 params.key + 1"," "+params.key+1);
                    }

                } catch (JsonIOException e) {
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
