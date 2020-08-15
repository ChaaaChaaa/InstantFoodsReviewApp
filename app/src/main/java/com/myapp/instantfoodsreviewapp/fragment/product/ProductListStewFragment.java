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
import java.util.List;


public class ProductListStewFragment extends Fragment {
    private RecyclerView recyclerViewStew;
    private CustomRecyclerAdapter adapterStew;
    private LinearLayoutManager layoutManagerStew;
    private List<ListItem> stewList = new ArrayList<>();
    private static final Integer STEW_CATEGORY = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stew, container, false);
        setHasOptionsMenu(true);
        recyclerViewStew = rootView.findViewById(R.id.recycler_stew);
        recyclerViewStew.setHasFixedSize(true);
        layoutManagerStew = new LinearLayoutManager(getActivity());
        recyclerViewStew.setLayoutManager(layoutManagerStew);
        initStew();
        return rootView;
    }

    private void initStew() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, STEW_CATEGORY);
        ProductViewModel productViewModelStew = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterStew = new CustomRecyclerAdapter();
        productViewModelStew.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterStew.submitList(products);
            }
        });
        recyclerViewStew.setAdapter(adapterStew);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu searchMenu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, searchMenu);
        MenuItem searchItem = searchMenu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterStew != null) {
                    adapterStew.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu, inflater);
    }
}
