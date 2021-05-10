package com.myapp.instantfoodsreviewapp.fragment.product;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.adapter.ProductViewModel;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;

import org.jetbrains.annotations.NotNull;

public class ProductListFriedRiceFragment extends Fragment {
    private RecyclerView recyclerViewRice;
    private CustomRecyclerAdapter adapterRice;
    private static final Integer RICE_CATEGORY = 1;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.myapp.instantfoodsreviewapp.databinding.FragmentFriedRiceBinding fragmentFriedRiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fried_rice, container, false);
        View rootView = fragmentFriedRiceBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewRice = rootView.findViewById(R.id.recycler_friedRice);
        recyclerViewRice.setHasFixedSize(true);
        LinearLayoutManager layoutManagerRice = new LinearLayoutManager(getActivity());
        recyclerViewRice.setLayoutManager(layoutManagerRice);
        initRice();
        return rootView;
    }


    private void initRice() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, RICE_CATEGORY);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        adapterRice = new CustomRecyclerAdapter();
        productViewModel.productPagedList.observe(getViewLifecycleOwner(), products -> adapterRice.submitList(products));
        recyclerViewRice.setAdapter(adapterRice);
    }
}