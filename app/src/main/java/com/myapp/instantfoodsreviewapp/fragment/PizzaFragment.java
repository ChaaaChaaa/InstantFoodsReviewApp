package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private FoodCategoryList foodCategoryList;

    private RecyclerView recyclerViewPizza;
    private RecyclerView.Adapter adapterPizza;
    private LinearLayoutManager linearLayoutManagerPizza;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pizza, container, false);

        recyclerViewPizza = rootView.findViewById(R.id.recycler_pizza);
        inputListData();
        adapterPizza = new CustomRecyclerAdapter(getActivity(), pizzaList);
        linearLayoutManagerPizza = new LinearLayoutManager(getActivity());

        recyclerViewPizza.setLayoutManager(linearLayoutManagerPizza);
        recyclerViewPizza.setAdapter(adapterPizza);
        return rootView;
    }

    private void inputListData() {

        pizzaList.add(new ListItem(R.drawable.pizza_odduggi, foodCategoryList.PIZZA,
                "[오뚜기] 콤비네이션피자 415g",
                "와....기대이상으로 푸짐하고 맛있네요!!! 제품 조리 안내대로 오븐에서 230도로 13분정도 구웠는데 간도 적당하고 치즈가 ?~늘어나서 으흠~~하면서 먹었어요ㅎㅎㅎ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        pizzaList.add(new ListItem(R.drawable.pizza_droetker, foodCategoryList.PIZZA,
                "닥터오트커 리스토란테 모짜렐라335g",
                "냉동피자중에 제일 맛있어요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        pizzaList.add(new ListItem(R.drawable.pizza_pulmuone, foodCategoryList.PIZZA,
                "풀무원 노엣지피자 베이컨파이브치즈 376g",
                "크기는 다른 피자에서 테두리 없어진 크기입니다. 치즈가 다양해서 기대했는데.ㅜㅜ 제 입맛엔 별루였어요ㅜㅜ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        pizzaList.add(new ListItem(R.drawable.pizza_nobrand, foodCategoryList.PIZZA,
                "[노브랜드] 콤비네이션피자 425 g",
                "가성비는 좋은데 내용물이 조금 빈약해 조금 더 지불하고 다른제품을 구매하려고요",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
    }
}
