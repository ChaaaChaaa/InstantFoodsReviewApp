package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;



import java.util.ArrayList;


public class ProductListFriedRiceFragment extends Fragment {
    private ArrayList<ListItem> riceList = new ArrayList<>();
    private RecyclerView recyclerViewRice;
    private CustomRecyclerAdapter adapterRice;
    private LinearLayoutManager layoutManagerRice;
    private static final String TAG = ProductListFriedRiceFragment.class.getSimpleName();

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
        //showRecyclerView();
        return rootView;
    }


    private void initRice() {

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

    private void getFriedRiceProductListInfo() {

    }


}
