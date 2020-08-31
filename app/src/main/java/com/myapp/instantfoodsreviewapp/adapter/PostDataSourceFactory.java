package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.myapp.instantfoodsreviewapp.model.Posts;

public class PostDataSourceFactory extends DataSource.Factory {
    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> postLiveDataSource = new MutableLiveData<>();
    int productId;

    PostDataSourceFactory(int productId){
        this.productId = productId;
    }


    @NonNull
    @Override
    public DataSource<Integer, Posts> create() {
        Log.e("2 productId"," "+productId);
        PostDataSource postDataSource = new PostDataSource(productId);
        postLiveDataSource.postValue(postDataSource);
        return postDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> getItemLiveDataSource() {
        return postLiveDataSource;
    }
}
