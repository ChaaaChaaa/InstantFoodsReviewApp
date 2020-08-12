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
public class ProductListNoodleFragment extends Fragment {
    private ArrayList<ListItem> noodleList = new ArrayList<>();
    private RecyclerView recyclerViewNoodle;
    private CustomRecyclerAdapter adapterNoodle;
    private LinearLayoutManager layoutManagerNoodle;
    private static final Integer NOODLE_CATEGORY = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noodle, container, false);
        setHasOptionsMenu(true);
        recyclerViewNoodle = rootView.findViewById(R.id.recycler_noodle);
        recyclerViewNoodle.setHasFixedSize(true);
        layoutManagerNoodle = new LinearLayoutManager(getActivity());
        recyclerViewNoodle.setLayoutManager(layoutManagerNoodle);
        initNoodle();
        // showRecyclerView();
        return rootView;
    }

    private void initNoodle() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, NOODLE_CATEGORY);
        ProductViewModel productViewModelNoodle = new ViewModelProvider(this).get(ProductViewModel.class);
        adapterNoodle = new CustomRecyclerAdapter();
        productViewModelNoodle.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterNoodle.submitList(products);
            }
        });
        recyclerViewNoodle.setAdapter(adapterNoodle);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu searchMenu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, searchMenu);
        MenuItem searchItem = searchMenu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search");
        //SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterNoodle != null) {
                    adapterNoodle.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu, inflater);
    }
}
