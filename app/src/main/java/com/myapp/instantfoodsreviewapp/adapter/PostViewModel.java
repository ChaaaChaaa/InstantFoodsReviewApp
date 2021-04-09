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
    PostDataSourceFactory postDataSourceFactory;

    public PostViewModel(int productId) {
        this.productId = productId;
        Log.e("1 productId", " " + productId);
        postDataSourceFactory = new PostDataSourceFactory(productId);
        liveDataSource = postDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();
        postPagedList = (new LivePagedListBuilder(postDataSourceFactory, config)).build();
    }

    public void refresh() {
        postDataSourceFactory.getItemLiveDataSource().getValue().invalidate();
    }
}
