package com.myapp.instantfoodsreviewapp.adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.myapp.instantfoodsreviewapp.model.Product;

public class ProductViewModel extends ViewModel {
    public LiveData<PagedList<Product>> productPagedList;

    public ProductViewModel() {
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, config)).build();
    }
}