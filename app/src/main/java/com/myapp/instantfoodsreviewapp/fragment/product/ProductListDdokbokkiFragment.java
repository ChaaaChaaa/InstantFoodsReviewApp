package com.myapp.instantfoodsreviewapp.fragment.product;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.adapter.ProductViewModel;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class ProductListDdokbokkiFragment extends Fragment {
    private RecyclerView recyclerViewDdokbokki;
    private CustomRecyclerAdapter adapterDdokbokki;
    private LinearLayoutManager layoutManagerDdokbokki;
    private static final Integer DDOCK_CATEGORY = 2;
    private ArrayList<ListItem> ddokbokkiList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ddokbokki, container, false);
        // View rootView = inflater.inflate(R.layout.fragment_ddokbokki, null);
        setHasOptionsMenu(true);
        recyclerViewDdokbokki = rootView.findViewById(R.id.recycler_ddokbokki);
        recyclerViewDdokbokki.setHasFixedSize(true);
        layoutManagerDdokbokki = new LinearLayoutManager(getActivity());
        recyclerViewDdokbokki.setLayoutManager(layoutManagerDdokbokki);

        initDdokbokki();
        //showRecyclerView();
        return rootView;
    }

    private void initDdokbokki() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, DDOCK_CATEGORY);
        ProductViewModel productViewModelDdok = new ViewModelProvider(this).get(ProductViewModel.class);
        adapterDdokbokki = new CustomRecyclerAdapter();
        productViewModelDdok.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterDdokbokki.submitList(products);
            }
        });
        recyclerViewDdokbokki.setAdapter(adapterDdokbokki);
    }



//    @Override
//    public void onPostsClick(int position) {
//        Intent intent = new Intent(this);
//        startActivity(intent);
//    }
}
