package com.myapp.instantfoodsreviewapp.fragment.product;

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


public class ProductListDumplingFragment extends Fragment {

    private ArrayList<ListItem> dumplingList = new ArrayList<>();
    private RecyclerView recyclerViewDumpling;
    private CustomRecyclerAdapter adapterDumpling;
    private LinearLayoutManager layoutManagerDumpling;
    private static final Integer DUMPLING_CATEGORY = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dumpling, container, false);
        setHasOptionsMenu(true);
        recyclerViewDumpling = rootView.findViewById(R.id.recycler_dumpling);
        recyclerViewDumpling.setHasFixedSize(true);
        layoutManagerDumpling = new LinearLayoutManager(getActivity());
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
