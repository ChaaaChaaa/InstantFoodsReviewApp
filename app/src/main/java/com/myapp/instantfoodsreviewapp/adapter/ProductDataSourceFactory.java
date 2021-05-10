package com.myapp.instantfoodsreviewapp.adapter;

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
        productLiveDataSource.postValue(productDataSource);
        return productDataSource;
    }
}