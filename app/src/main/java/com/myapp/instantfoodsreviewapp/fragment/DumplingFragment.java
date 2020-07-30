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


public class DumplingFragment extends Fragment {

    private ArrayList<ListItem> dumplingList = new ArrayList<>();
    private RecyclerView recyclerViewDumpling;
    private CustomRecyclerAdapter adapterDumpling;
    private LinearLayoutManager layoutManagerDumpling;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dumpling, container, false);
        setHasOptionsMenu(true);
        recyclerViewDumpling = rootView.findViewById(R.id.recycler_dumpling);
        recyclerViewDumpling.setHasFixedSize(true);
        layoutManagerDumpling = new LinearLayoutManager(getActivity());
        recyclerViewDumpling.setLayoutManager(layoutManagerDumpling);
        initDumpling();
        showRecyclerView();
        return rootView;
    }

    private void showRecyclerView() {
        adapterDumpling = new CustomRecyclerAdapter(getActivity(), dumplingList);
        recyclerViewDumpling.setAdapter(adapterDumpling);
    }


    private void initDumpling() {
        dumplingList.add(new ListItem(R.drawable.dumpling_nobrand, FoodCategoryList.DUMPLING,
                "[노브랜드] 바삭 냉동 군 만두 1kg",
                "개인적인 입맛?생각?이지만 노브랜드 만두 중에서 바삭군만두가 젤 맛있어요 교자만두도 맛있는데 이게 속이 더 알찬거같아서 이걸 자주 사먹는편이에요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        dumplingList.add(new ListItem(R.drawable.dumpling_bibigo, FoodCategoryList.DUMPLING,
                "[CJ] 비비고왕교자490gx2",
                "비비고 제품을 특히 더 좋아하는 이유는 냉동 만두 특유에 비릿한 고기맛이 없고 만두피도 상당히 야들야들하다. 고급진 한끼를 먹는 느낌이랄까!! 고기와 야채가 꽉꽉 차있어서 식감도 굉장이 좋다.",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        dumplingList.add(new ListItem(R.drawable.dumpling_pulmuone, FoodCategoryList.DUMPLING,
                "[풀무원] 얇은피꽉찬속 고기만두 400g*2",
                "요즘 만두 중 최고인것 같습니다 살짝 들큰한 맛이 쪼끔 그렇지만.",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        dumplingList.add(new ListItem(R.drawable.dumpling_odduggi, FoodCategoryList.DUMPLING,
                "[오뚜기] 육즙이 풍부한 등심 돈까스(100g*5개입) 500g",
                "오래전 부터 꾸준히 구입해온 오뚜기 돈까스 입니다.가격대비 품질좋은 상품이라 늘 먹을때마다 칭찬하고 싶은 상품입니다.외식으로 워낙 맛있는 돈까스로 길들여진 입들이라 가족모두 만족하기에는 냉동식품이 어느정도 한계가 있는데 이 상품은 고기 육질도 씹는식감도 모두 좋고 무엇보다 잡냄새가 없어서 젤 좋습니다.",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
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
                if (adapterDumpling != null) {
                    adapterDumpling.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu, inflater);
    }
}
