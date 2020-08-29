package com.myapp.instantfoodsreviewapp.adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Posts;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;

import java.util.concurrent.Executor;

import retrofit2.http.Query;

public class PostViewModel extends ViewModel {
    public LiveData<PagedList<Posts>> postPagedList;
    private LiveData liveDataSource;
    private LiveData<PageKeyedDataSource<Integer, Posts>> newDataSource;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    private PostDataSourceFactory postDataSourceFactory;
    private Executor executor;
    private String searchKey;

//    public PostViewModel() {
//        postDataSourceFactory = new PostDataSourceFactory();
//        liveDataSource = postDataSourceFactory.getItemLiveDataSource();
//        PagedList.Config config =
//                (new PagedList.Config.Builder())
//                        .setEnablePlaceholders(false)
//                        .setPageSize(ProductDataSource.PAGE_SIZE)
//                        .build();
//      //  postDataSourceFactory = RetrofitInterface.
//       postPagedList = (new LivePagedListBuilder(postDataSourceFactory, config)).build();
////        postPagedList = Transformations.switchMap(filterTextAll,input -> {
////            if(input == null || input.equals("") || input.equals("%%")){
////                return new LivePagedListBuilder<>(postDataSourceFactory, config)
////                        .build();
////            }
////        });
//
//
////        liveDataSource = Transformations.switchMap(filterTextAll,input -> {
////            postDataSourceFactory = new PostDataSourceFactory(executor,input);
////            postPagedList = Transformations.switchMap(liveDataSource, newDataSource -> newDataSource.);
////            return (new LivePagedListBuilder(postDataSourceFactory, config))
////                    .setFetchExecutor(executor)
////                    .build();
////        });
//
//    }
//
//    private void performSearch(Query keyword){
//
//
//    }

    public PostViewModel() {
        PostDataSourceFactory postDataSourceFactory = new PostDataSourceFactory();
        liveDataSource = postDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();
        postPagedList = (new LivePagedListBuilder(postDataSourceFactory, config)).build();
    }
}
