package com.myapp.instantfoodsreviewapp.adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Product;

public class ProductViewModel extends ViewModel {
   public LiveData<PagedList<Product>> productPagedList;
   private LiveData<PageKeyedDataSource<Integer,Product>> liveDataSource;

    public ProductViewModel(){
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();
        liveDataSource = productDataSourceFactory.getProductLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(ProductDataSource.PAGE_SIZE)
                .build();
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory,config)).build();
    }
}
