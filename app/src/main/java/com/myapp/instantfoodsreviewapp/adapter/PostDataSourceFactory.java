package com.myapp.instantfoodsreviewapp.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.myapp.instantfoodsreviewapp.model.Posts;

public class PostDataSourceFactory extends DataSource.Factory {
    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> postLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Posts> create() {
        PostDataSource postDataSource = new PostDataSource();
        postLiveDataSource.postValue(postDataSource);
        return postDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> getItemLiveDataSource() {
        return postLiveDataSource;
    }
}
