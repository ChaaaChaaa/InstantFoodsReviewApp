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

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaFragment extends Fragment {
    private ArrayList<ListItem> pizzaList = new ArrayList<>();
    private RecyclerView recyclerViewPizza;
    private CustomRecyclerAdapter adapterPizza;
    private LinearLayoutManager layoutManagerPizza;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pizza, container, false);
        setHasOptionsMenu(true);
        recyclerViewPizza = rootView.findViewById(R.id.recycler_pizza);
        recyclerViewPizza.setHasFixedSize(true);
        layoutManagerPizza = new LinearLayoutManager(getActivity());
        recyclerViewPizza.setLayoutManager(layoutManagerPizza);
        initPizza();
        showRecyclerView();
        return rootView;
    }

    private void showRecyclerView() {
        adapterPizza = new CustomRecyclerAdapter(getActivity(), pizzaList);
        recyclerViewPizza.setAdapter(adapterPizza);
    }

    private void initPizza() {
        pizzaList.add(new ListItem(R.drawable.pizza_odduggi, FoodCategoryList.PIZZA,
                "[오뚜기] 콤비네이션피자 415g",
                "와....기대이상으로 푸짐하고 맛있네요!!! 제품 조리 안내대로 오븐에서 230도로 13분정도 구웠는데 간도 적당하고 치즈가 ?~늘어나서 으흠~~하면서 먹었어요ㅎㅎㅎ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        pizzaList.add(new ListItem(R.drawable.pizza_droetker, FoodCategoryList.PIZZA,
                "닥터오트커 리스토란테 모짜렐라335g",
                "냉동피자중에 제일 맛있어요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        pizzaList.add(new ListItem(R.drawable.pizza_pulmuone, FoodCategoryList.PIZZA,
                "풀무원 노엣지피자 베이컨파이브치즈 376g",
                "크기는 다른 피자에서 테두리 없어진 크기입니다. 치즈가 다양해서 기대했는데.ㅜㅜ 제 입맛엔 별루였어요ㅜㅜ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        pizzaList.add(new ListItem(R.drawable.pizza_nobrand, FoodCategoryList.PIZZA,
                "[노브랜드] 콤비네이션피자 425 g",
                "가성비는 좋은데 내용물이 조금 빈약해 조금 더 지불하고 다른제품을 구매하려고요",
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
                if(adapterPizza != null){
                    adapterPizza.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu,inflater);
    }
}
