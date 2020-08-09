package com.myapp.instantfoodsreviewapp.adapter;

import android.app.usage.NetworkStats;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Product;

public class ProductViewModel extends ViewModel {
    //상태변화가 있을때 값이 변화 유무
    public LiveData<PagedList<Product>> productPagedList;
    public LiveData<NetworkStats> networkState;
    private LiveData<PageKeyedDataSource<Integer, Product>> liveDataSource;


    public ProductViewModel() {
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();

        //networkState = Transformations.switchMap(ProductDataSourceFactory.)



        liveDataSource = productDataSourceFactory.productLiveDataSource;
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, config)).build();
        Log.e("111 productPagedList"," "+productPagedList.getValue()); // null
    }
}
