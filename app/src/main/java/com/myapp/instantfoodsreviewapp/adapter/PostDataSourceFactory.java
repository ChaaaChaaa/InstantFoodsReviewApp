package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.myapp.instantfoodsreviewapp.model.Post;

public class PostDataSourceFactory extends DataSource.Factory {
    public MutableLiveData<PageKeyedDataSource<Integer, Post>> postLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource create() {
        PostDataSource postDataSource = new PostDataSource();
        Log.e("111 ProductDataSource", " " + postDataSource.toString()); //10
        postLiveDataSource.postValue(postDataSource);
        return postDataSource;
    }
}
