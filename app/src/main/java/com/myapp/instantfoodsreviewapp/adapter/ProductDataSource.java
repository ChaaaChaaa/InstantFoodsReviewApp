package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.JsonIOException;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListFriedRiceFragment;
import com.myapp.instantfoodsreviewapp.model.Post;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.model.ProductResponse;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import com.myapp.instantfoodsreviewapp.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {
    private static final String TAG = "ProductDataSource";
    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private int currentCategoryId;
    public List<Post> productList = new ArrayList<>();
    public List<PostMultipleItemTypeInterface> postMultipleItemTypeInterfaces = new ArrayList<PostMultipleItemTypeInterface>();
    private UserPreference userPreference;



    public void setCurrentCategoryId(){
      currentCategoryId =  userPreference.getInstance().getInt(Config.KEY_CATEGORY);
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        setCurrentCategoryId();
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Log.e("444 currentCategoryId2"," "+currentCategoryId);

        Call<ProductResponse> productResponseCall = retrofitInterface.list(currentCategoryId, PAGE_SIZE, FIRST_PAGE);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    ProductResponse productResponse = response.body();
                    if (response.isSuccessful()) {

                        int productListSize = response.body().getResultData().size();
                        Log.e("444 currentCategoryId"," "+currentCategoryId);
                        Log.e("111 productListSize", " " + productListSize);

                       for (int i = 0; i < productListSize; i++) {
                            String productPicture = response.body().getResultData().get(i).getPrImage();
                            int productCategory = response.body().getResultData().get(i).getPrCategory();
                            String title = response.body().getResultData().get(i).getPrTitle();
                            int reviewCount = response.body().getResultData().get(i).getPrReviewCount();
                            int productScore = response.body().getResultData().get(i).getPrScore();
                            postMultipleItemTypeInterfaces.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
                        }


                        Log.e("111 ProductList", " " + productList.size()); //10

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
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(currentCategoryId, PAGE_SIZE, params.key);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    ProductResponse productResponse = response.body();

                    if (response.isSuccessful()) {
                        List<Product> responseItems = productResponse.getResultData();
                        Integer key = beforePageKey(params);

                        Log.e("222 ProductList2", " " + productList.size()); // 안옴

                        int productListSize = response.body().getResultData().size();

                        for (int i = key; i < productListSize; i++) {
                            String productPicture = response.body().getResultData().get(i).getPrImage();
                            int productCategory = response.body().getResultData().get(i).getPrCategory();
                            String title = response.body().getResultData().get(i).getPrTitle();
                            int reviewCount = response.body().getResultData().get(i).getPrReviewCount();
                            int productScore = response.body().getResultData().get(i).getPrScore();
                            postMultipleItemTypeInterfaces.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
                        }
                        Log.e("222 params.key ", " " + params.key);
                        callback.onResult(responseItems, params.key-1);

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
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
        Call<ProductResponse> productResponseCall = retrofitInterface.list(currentCategoryId, PAGE_SIZE, params.key);
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();

                try {
                    if (response.isSuccessful()) {
                        Integer key = afterPageKey(params);
                        List<Product> responseItems = productResponse.getResultData();
                        Log.e("222 ProductList3", " " + productList.size());
                        int productListSize = response.body().getResultData().size();

                        for (int i = key; i < productListSize; i++) {
                            String productPicture = response.body().getResultData().get(i).getPrImage();
                            int productCategory = response.body().getResultData().get(i).getPrCategory();
                            String title = response.body().getResultData().get(i).getPrTitle();
                            int reviewCount = response.body().getResultData().get(i).getPrReviewCount();
                            int productScore = response.body().getResultData().get(i).getPrScore();
                            postMultipleItemTypeInterfaces.add(new Product(productPicture, productCategory, title, reviewCount, productScore));
                            //  productList.add(new ListItem(productPicture,productCategory,title,reviewCount,productScore));
                        }

                        callback.onResult(responseItems, params.key+1);
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
