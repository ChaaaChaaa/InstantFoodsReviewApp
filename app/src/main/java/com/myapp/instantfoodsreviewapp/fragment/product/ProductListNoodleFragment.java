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
import com.myapp.instantfoodsreviewapp.databinding.FragmentNoodleBinding;
import com.myapp.instantfoodsreviewapp.preference.UserPreference;
import com.myapp.instantfoodsreviewapp.utils.Config;

import org.jetbrains.annotations.NotNull;


public class ProductListNoodleFragment extends Fragment {
    private RecyclerView recyclerViewNoodle;
    private CustomRecyclerAdapter adapterNoodle;
    private static final Integer NOODLE_CATEGORY = 3;
    private FragmentNoodleBinding fragmentNoodleBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentNoodleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_noodle, container, false);
        View rootView = fragmentNoodleBinding.getRoot();
        setHasOptionsMenu(true);
        recyclerViewNoodle = rootView.findViewById(R.id.recycler_noodle);
        recyclerViewNoodle.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNoodle = new LinearLayoutManager(getActivity());
        recyclerViewNoodle.setLayoutManager(layoutManagerNoodle);
        initNoodle();
        // showRecyclerView();
        return rootView;
    }

    private void initNoodle() {
        UserPreference.getInstance().putInt(Config.KEY_CATEGORY, NOODLE_CATEGORY);
        ProductViewModel productViewModelNoodle = new ViewModelProvider(this).get(ProductViewModel.class);

        adapterNoodle = new CustomRecyclerAdapter();
        productViewModelNoodle.productPagedList.observe(getViewLifecycleOwner(), products -> adapterNoodle.submitList(products));
        recyclerViewNoodle.setAdapter(adapterNoodle);
    }
}
