package com.myapp.instantfoodsreviewapp.fragment.product;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.adapter.ProductViewModel;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;

import org.jetbrains.annotations.NotNull;

public class ProductListDdokbokkiFragment extends Fragment {
    private RecyclerView recyclerViewDdokbokki;
    private CustomRecyclerAdapter adapterDdokbokki;
    private static final Integer DDOCK_CATEGORY = 2;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.myapp.instantfoodsreviewapp.databinding.FragmentDdokbokkiBinding fragmentDdokbokkiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ddokbokki, container, false);
        View rootView = fragmentDdokbokkiBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewDdokbokki = rootView.findViewById(R.id.recycler_ddokbokki);
        recyclerViewDdokbokki.setHasFixedSize(true);
        LinearLayoutManager layoutManagerDdokbokki = new LinearLayoutManager(getActivity());
        recyclerViewDdokbokki.setLayoutManager(layoutManagerDdokbokki);

        initDdokbokki();
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
}