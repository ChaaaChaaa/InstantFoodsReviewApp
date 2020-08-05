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
import java.util.List;


public class StewFragment extends Fragment {
    private RecyclerView recyclerViewStew;
    private CustomRecyclerAdapter adapterStew;
    private LinearLayoutManager layoutManagerStew;
   // private SearchFragment searchFragment;
    private List<ListItem> stewList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stew, container, false);
       // View rootView = inflater.inflate(R.layout.fragment_stew, null);
        setHasOptionsMenu(true);
       // ArrayList<ListItem> stewList = new ArrayList<>();
        recyclerViewStew = rootView.findViewById(R.id.recycler_stew);
        recyclerViewStew.setHasFixedSize(true);
        layoutManagerStew = new LinearLayoutManager(getActivity());
        recyclerViewStew.setLayoutManager(layoutManagerStew);
       // initStew();
       // showRecyclerView();
       // searchFragment = new SearchFragment(adapterStew);
        return rootView;
    }

//    private void showRecyclerView(){
//        adapterStew = new CustomRecyclerAdapter(getActivity(),stewList);
//        recyclerViewStew.setAdapter(adapterStew);
//    }
//
//    private void initStew() {
//        stewList.add(new ListItem(R.drawable.stew_peacock, FoodCategoryList.STEW,
//                "[피코크] 진한 육개장 500g",
//                "피코크 진한 육개장은 두 식구가 먹기에 딱 적절한 양입니다. 저는 신혼부부인데 남편이랑 주말에 점심으로 해 먹었습니다. (해 먹었다기엔 너무 거창하고 그냥 데워 먹은거네요..) 1~2인분이라고 되어있는데 햇반이랑 먹으면 2인이 적당히 배부르게 잘 먹을 수 있을 것 같습니다.",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full));
//
//        stewList.add(new ListItem(R.drawable.stew_pulmuone, FoodCategoryList.STEW,
//                "풀무원 정통순두부찌개 Kit 602g",
//                "집에서 순두부찌개하기 어려웠는데 양념장다들어있어서 따로 간을 안해도되네요 남편이 제가한줄알고 놀래더군요 ㅎㅎㅎ",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_half));
//
//        stewList.add(new ListItem(R.drawable.stew_bibigo, FoodCategoryList.STEW,
//                "CJ 비비고 차돌육개장 500g",
//                "일반 육개장보다 밍밍하니 별루에요 그냥 비비고 육개장 드시는게 훨씬 맛잇어요",
//                R.drawable.ic_star_full,
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank));
//
//        stewList.add(new ListItem(R.drawable.stew_odduggi, FoodCategoryList.STEW,
//                "오뚜기 간편 미역국 5입(9.5g*5)",
//                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank,
//                R.drawable.ic_star_blank));
//    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu searchMenu, @NonNull MenuInflater inflater) {
        // super.onCreateOptionsMenu(searchMenu, inflater);
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
                if(adapterStew != null){
                    adapterStew.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu,inflater);
    }
}
