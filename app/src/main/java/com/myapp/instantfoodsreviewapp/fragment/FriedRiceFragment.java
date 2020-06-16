package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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
import com.myapp.instantfoodsreviewapp.model.FoodCategoryList;
import com.myapp.instantfoodsreviewapp.model.ListItem;

import java.util.ArrayList;


public class FriedRiceFragment extends Fragment {
    private ArrayList<ListItem> riceList = new ArrayList<>();
    private RecyclerView recyclerViewRice;
    private CustomRecyclerAdapter adapterRice;
    private LinearLayoutManager layoutManagerRice;

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
        showRecyclerView();
        return rootView;
    }

    private void showRecyclerView() {
        adapterRice = new CustomRecyclerAdapter(getActivity(), riceList);
        recyclerViewRice.setAdapter(adapterRice);
    }

    private void initRice() {

        riceList.add(new ListItem(R.drawable.rice_peacock, FoodCategoryList.DDOKBOKKI,
                "피코크 새우볶음밥 4+2기획 1260g",
                " 늘 먹던거에요 ㅎㅎㅎ 맛있어요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        riceList.add(new ListItem(R.drawable.rice_bibigo, FoodCategoryList.DDOKBOKKI,
                "CJ 차돌깍두기볶음밥 410g",
                "맛있네요 저는 맨밥 조금 더넣고 들기름넣고먹어요 개꿀맛",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        riceList.add(new ListItem(R.drawable.rice_chunjungone, FoodCategoryList.DDOKBOKKI,
                "[청정원] 매운곱창볶음밥 400g",
                "맛있어서 재구매~ 매콤하고 곱창은 조금 적게 들어가있습니다 냉동싫어하는 남편이 이건맛있대요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        riceList.add(new ListItem(R.drawable.rice_odduggi, FoodCategoryList.DDOKBOKKI,
                "오뚜기 철판감자탕볶음밥 450g",
                "리뷰 보고 구매했는데 정말 감자탕 볶음밥향이 가득해요 만족합니다. 감자탕을 먹고난후 볶음밥을 먹는 느낌 딱 좋아요",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu searchMenu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,searchMenu);
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
                if(adapterRice != null){
                    adapterRice.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu,inflater);
    }
}
