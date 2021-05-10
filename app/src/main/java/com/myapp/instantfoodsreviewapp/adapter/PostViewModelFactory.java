package com.myapp.instantfoodsreviewapp.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PostViewModelFactory implements ViewModelProvider.Factory {
    private final int productId;

    public PostViewModelFactory(int productId) {
        this.productId = productId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostViewModel(productId);
    }
}
