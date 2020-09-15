package com.myapp.instantfoodsreviewapp.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.myapp.instantfoodsreviewapp.model.Posts;

import java.util.concurrent.Executor;

public class PostDataSourceFactory extends DataSource.Factory {
    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> postLiveDataSource = new MutableLiveData<>();//    private Executor executor;
//    private String searchKey;
//    private PostDataSource postDataSource= new MutableLiveData<>();

//    PostDataSourceFactory(Executor executor, String searchKey){
//        this.executor = executor;
//        this.searchKey = searchKey;
//        this.postLiveDataSource = new MutableLiveData<>();
//    }


//    @NonNull
//    @Override
//    public DataSource<Integer, Posts> create() {
//        //PostDataSource postDataSource = new PostDataSource(executor,searchKey);
//        postLiveDataSource.postValue(postDataSource);
//        return postDataSource;
//    }
//
//    public MutableLiveData<PageKeyedDataSource<Integer, Posts>> getItemLiveDataSource() {
//        return postLiveDataSource;
//    }
//
//    public PostDataSource getPostDataSource(){
//        return postDataSource;
//    }



    @Override
    public DataSource<Integer, Posts> create() {
        PostDataSource postDataSource = new PostDataSource();
        postLiveDataSource.postValue(postDataSource);
        return postDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer, Posts>> getItemLiveDataSource() {
        return postLiveDataSource;
    }

}
