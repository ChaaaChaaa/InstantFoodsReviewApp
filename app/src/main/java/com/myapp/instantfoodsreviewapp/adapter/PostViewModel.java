package com.myapp.instantfoodsreviewapp.adapter;

import android.app.usage.NetworkStats;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Post;

public class PostViewModel extends ViewModel {
    public LiveData<PagedList<Post>> postPagedList;
    private LiveData<PageKeyedDataSource<Integer, Post>> liveDataSource;

    private int currentCategoryId;

    public PostViewModel() {
        PostDataSourceFactory postDataSourceFactory = new PostDataSourceFactory();
        liveDataSource = postDataSourceFactory.postLiveDataSource;
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();
        postPagedList = (new LivePagedListBuilder(postDataSourceFactory, config)).build();
       // Log.e("111 postDataSourceFactory"," "+postPagedList.getValue()); // null
    }
}
