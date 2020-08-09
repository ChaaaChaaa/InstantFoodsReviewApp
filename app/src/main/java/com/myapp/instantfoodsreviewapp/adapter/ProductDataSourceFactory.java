package com.myapp.instantfoodsreviewapp.adapter;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.myapp.instantfoodsreviewapp.model.Product;

public class ProductDataSourceFactory extends DataSource.Factory {

    public MutableLiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        ProductDataSource productDataSource = new ProductDataSource();
        Log.e("111 ProductDataSource"," "+productDataSource.toString()); //10
        productLiveDataSource.postValue(productDataSource);
        return productDataSource;
    }
//
//    public MutableLiveData<PageKeyedDataSource<Integer,Product>> getProductLiveDataSource(){
//        return productLiveDataSource;
//    }
}
