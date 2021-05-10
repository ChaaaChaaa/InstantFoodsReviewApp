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

public class ProductListDumplingFragment extends Fragment {
    private RecyclerView recyclerViewDumpling;
    private CustomRecyclerAdapter adapterDumpling;
    private static final Integer DUMPLING_CATEGORY = 4;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.myapp.instantfoodsreviewapp.databinding.FragmentDumplingBinding fragmentDumplingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dumpling, container, false);
        View rootView = fragmentDumplingBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewDumpling = rootView.findViewById(R.id.recycler_dumpling);
        recyclerViewDumpling.setHasFixedSize(true);
        LinearLayoutManager layoutManagerDumpling = new LinearLayoutManager(getActivity());
        recyclerViewDumpling.setLayoutManager(layoutManagerDumpling);
        initDumpling();
        return rootView;
    }

    private void initDumpling() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, DUMPLING_CATEGORY);
        ProductViewModel productViewModelDumpling = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterDumpling = new CustomRecyclerAdapter();
        productViewModelDumpling.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterDumpling.submitList(products);
            }
        });
        recyclerViewDumpling.setAdapter(adapterDumpling);
    }
}