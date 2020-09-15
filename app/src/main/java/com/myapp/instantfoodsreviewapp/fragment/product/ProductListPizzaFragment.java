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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListPizzaFragment extends Fragment {
    private ArrayList<ListItem> pizzaList = new ArrayList<>();
    private RecyclerView recyclerViewPizza;
    private CustomRecyclerAdapter adapterPizza;
    private LinearLayoutManager layoutManagerPizza;
    private static final Integer PIZZA_CATEGORY = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pizza, container, false);
        setHasOptionsMenu(true);
        recyclerViewPizza = rootView.findViewById(R.id.recycler_pizza);
        recyclerViewPizza.setHasFixedSize(true);
        layoutManagerPizza = new LinearLayoutManager(getActivity());
        recyclerViewPizza.setLayoutManager(layoutManagerPizza);
        initPizza();
        // showRecyclerView();
        return rootView;
    }

    private void initPizza() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, PIZZA_CATEGORY);
        ProductViewModel productViewModelPizza = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterPizza = new CustomRecyclerAdapter();
        productViewModelPizza.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterPizza.submitList(products);
            }
        });
        recyclerViewPizza.setAdapter(adapterPizza);
    }


}
