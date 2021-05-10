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

public class ProductListStewFragment extends Fragment {
    private RecyclerView recyclerViewStew;
    private CustomRecyclerAdapter adapterStew;
    private static final Integer STEW_CATEGORY = 6;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.myapp.instantfoodsreviewapp.databinding.FragmentStewBinding fragmentStewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stew, container, false);
        View rootView = fragmentStewBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewStew = rootView.findViewById(R.id.recycler_stew);
        recyclerViewStew.setHasFixedSize(true);
        LinearLayoutManager layoutManagerStew = new LinearLayoutManager(getActivity());
        recyclerViewStew.setLayoutManager(layoutManagerStew);
        initStew();
        return rootView;
    }

    private void initStew() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, STEW_CATEGORY);
        ProductViewModel productViewModelStew = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterStew = new CustomRecyclerAdapter();
        productViewModelStew.productPagedList.observe(getViewLifecycleOwner(), products -> adapterStew.submitList(products));
        recyclerViewStew.setAdapter(adapterStew);
    }
}
