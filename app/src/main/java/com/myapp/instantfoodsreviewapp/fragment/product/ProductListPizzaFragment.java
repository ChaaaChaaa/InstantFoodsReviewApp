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


public class ProductListPizzaFragment extends Fragment {
    private RecyclerView recyclerViewPizza;
    private CustomRecyclerAdapter adapterPizza;
    private static final Integer PIZZA_CATEGORY = 5;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.myapp.instantfoodsreviewapp.databinding.FragmentPizzaBinding fragmentPizzaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pizza, container, false);
        View rootView = fragmentPizzaBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewPizza = rootView.findViewById(R.id.recycler_pizza);
        recyclerViewPizza.setHasFixedSize(true);
        LinearLayoutManager layoutManagerPizza = new LinearLayoutManager(getActivity());
        recyclerViewPizza.setLayoutManager(layoutManagerPizza);
        initPizza();
        return rootView;
    }

    private void initPizza() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, PIZZA_CATEGORY);
        ProductViewModel productViewModelPizza = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterPizza = new CustomRecyclerAdapter();
        productViewModelPizza.productPagedList.observe(getViewLifecycleOwner(), products -> adapterPizza.submitList(products));
        recyclerViewPizza.setAdapter(adapterPizza);
    }
}