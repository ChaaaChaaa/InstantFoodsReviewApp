package com.myapp.instantfoodsreviewapp.adapter;

import android.view.View;

import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.List;

public interface OnItemClickListener {
    void onItemClick(View view, int position, List<Product> products);
}
