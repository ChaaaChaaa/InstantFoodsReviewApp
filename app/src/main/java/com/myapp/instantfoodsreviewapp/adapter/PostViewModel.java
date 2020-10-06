package com.myapp.instantfoodsreviewapp.adapter;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Posts;

public class PostViewModel extends ViewModel {
    public LiveData<PagedList<Posts>> postPagedList;
    private LiveData<PageKeyedDataSource<Integer, Posts>> liveDataSource;


    int productId;


    public PostViewModel(int productId) {
        this.productId = productId;
        Log.e("1 productId"," "+productId);
        PostDataSourceFactory postDataSourceFactory = new PostDataSourceFactory(productId);
        liveDataSource = postDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();
        postPagedList = (new LivePagedListBuilder(postDataSourceFactory, config)).build();
    }
}
