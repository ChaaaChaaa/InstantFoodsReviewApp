package com.myapp.instantfoodsreviewapp.fragment.product;

import android.content.SharedPreferences;
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
import com.myapp.instantfoodsreviewapp.adapter.ProductDataSource;
import com.myapp.instantfoodsreviewapp.adapter.ProductDataSourceFactory;
import com.myapp.instantfoodsreviewapp.adapter.ProductViewModel;
import com.myapp.instantfoodsreviewapp.dialog.TransferDataCallback;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;

public class ProductListFriedRiceFragment extends Fragment {
    private RecyclerView recyclerViewRice;
    private CustomRecyclerAdapter adapterRice;
    private LinearLayoutManager layoutManagerRice;
    private static final String TAG = ProductListFriedRiceFragment.class.getSimpleName();
    private TransferDataCallback<Integer> categoryCallback;
    private static final Integer RICE_CATEGORY = 1;
    private UserPreference sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fried_rice, container, false);
        setHasOptionsMenu(true);
        recyclerViewRice = rootView.findViewById(R.id.recycler_friedRice);
        recyclerViewRice.setHasFixedSize(true);
        layoutManagerRice = new LinearLayoutManager(getActivity());
        recyclerViewRice.setLayoutManager(layoutManagerRice);
        initRice();
        //setResultCategoryCallback();

        return rootView;
    }


    private void initRice() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, RICE_CATEGORY);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
       adapterRice = new CustomRecyclerAdapter();
        productViewModel.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                adapterRice.submitList(products);
            }
        });
        recyclerViewRice.setAdapter(adapterRice);
    }


    private void setResultCategoryCallback() {
        categoryCallback.transfer(RICE_CATEGORY);
    }

    public TransferDataCallback<Integer> getCategoryCallback() {
        return categoryCallback;
    }

    public void setCategoryCallback(TransferDataCallback<Integer> categoryCallback) {
        this.categoryCallback = categoryCallback;
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
                if (adapterRice != null) {
                    adapterRice.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu, inflater);
    }


}
