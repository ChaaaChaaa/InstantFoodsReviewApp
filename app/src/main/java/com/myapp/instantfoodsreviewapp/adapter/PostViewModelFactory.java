package com.myapp.instantfoodsreviewapp.adapter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class PostViewModelFactory implements ViewModelProvider.Factory {
  //  private Application application;
    //private List<Product> pickProduct = new ArrayList<>();

    private int productId;

//    public PostViewModelFactory(Application application,List<Product> pickProduct ){
//        this.application = application;
//        this.pickProduct = pickProduct;
//    }

    public PostViewModelFactory(int productId){
        this.productId =productId;
    }


    @NonNull

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        T t = (T) new PostViewModelFactory(application,pickProduct);
//        return t;
        return (T) new PostViewModel(productId);

        //return modelClass.getConstructor(String.class).newInstance(this.pickProduct);
        //        try {
//            return modelClass.getConstructor(String.class).newInstance(this.productId);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }






    }
}
