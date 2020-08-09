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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.CustomRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.adapter.ProductDataSourceFactory;
import com.myapp.instantfoodsreviewapp.adapter.ProductViewModel;
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;
import com.myapp.instantfoodsreviewapp.model.Product;
import com.myapp.instantfoodsreviewapp.model.ProductResponse;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.model.entity.ProductListDto;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitClient;
import com.myapp.instantfoodsreviewapp.restapi.RetrofitInterface;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
////        ProductViewModel productViewModel
////                = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProductViewModel.class);
//
//
//
//        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
//
////        ProductViewModel productViewModel1
////                = new ViewModelProvider(this).get(ProductViewModel.class);
//
//        productViewModel.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
//            @Override
//            public void onChanged(PagedList<Product> products) {
//                Log.e("111 PagedList"," "+products.size());
//                adapterRice.submitList(products);
//            }
//        });

//        adapterRice = new CustomRecyclerAdapter();
//        productViewModel.productPagedList.observeForever(this,adapterRice::submitList);
//        recyclerViewRice.setAdapter(adapterRice);


//    }
//    public ProductListFriedRiceFragment(CustomRecyclerAdapter adapterRice){
//        recyclerViewRice.setAdapter(adapterRice);
//    }

//    private void showRecyclerView() {
//        adapterRice = new CustomRecyclerAdapter(getActivity(), riceList);
//        recyclerViewRice.setAdapter(adapterRice);
//    }

    private void initRice() {
//        riceList.add(new ListItem(R.drawable.rice_peacock, FoodCategoryList.DDOKBOKKI,
//                "피코크 새우볶음밥 4+2기획 1260g",
//                " 늘 먹던거에요 ㅎㅎㅎ 맛있어요",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full));
//
//        riceList.add(new ListItem(R.drawable.rice_bibigo, FoodCategoryList.DDOKBOKKI,
//                "CJ 차돌깍두기볶음밥 410g",
//                "맛있네요 저는 맨밥 조금 더넣고 들기름넣고먹어요 개꿀맛",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_half));
//
//        riceList.add(new ListItem(R.drawable.rice_chunjungone, FoodCategoryList.DDOKBOKKI,
//                "[청정원] 매운곱창볶음밥 400g",
//                "맛있어서 재구매~ 매콤하고 곱창은 조금 적게 들어가있습니다 냉동싫어하는 남편이 이건맛있대요",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank));
//
//        riceList.add(new ListItem(R.drawable.rice_odduggi, FoodCategoryList.DDOKBOKKI,
//                "오뚜기 철판감자탕볶음밥 450g",
//                "리뷰 보고 구매했는데 정말 감자탕 볶음밥향이 가득해요 만족합니다. 감자탕을 먹고난후 볶음밥을 먹는 느낌 딱 좋아요",
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank));
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
       adapterRice = new CustomRecyclerAdapter();
//        ProductViewModel productViewModel
//                = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ProductViewModel.class);
//
////        ProductViewModel productViewModel1
////                = new ViewModelProvider(this).get(ProductViewModel.class);
//
        productViewModel.productPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                Log.e("111 PagedList"," "+products.size()); //0
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

    int categoryId = 1;
    int size = 10;
    int page = 1;

//    private void getFriedRiceProductList() {
//        RetrofitInterface retrofitInterface = RetrofitClient.getRestMethods();
//        Call<ProductResponse> productResponseCall = retrofitInterface.list(categoryId, size, page);
//        productResponseCall.enqueue(new Callback<ProductResponse>() {
//            @Override
//            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
//                if (response.isSuccessful()) {
//                    List<Product> products = new ArrayList<>();
//                    if (response.body().getResultData() != null) {
//                        products = response.body().getResultData();
//
//                    } else {
//                        Log.d(TAG, "Number of products received: " + products.size());
//                    }
//                }
//
//             riceList.add(new ListItem())
//
//            }
//
//            @Override
//            public void onFailure(Call<ProductResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//
//            }
//        });
//
//    }
}
