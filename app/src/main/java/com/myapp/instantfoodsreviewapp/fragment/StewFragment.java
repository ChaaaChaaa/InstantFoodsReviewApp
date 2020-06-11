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
import java.util.List;


public class StewFragment extends Fragment {
    private RecyclerView recyclerViewStew;
    private RecyclerView.Adapter adapterStew;
    private LinearLayoutManager layoutManagerStew;
    private ArrayList<ListItem> stewList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stew, null);


       // ArrayList<ListItem> stewList = new ArrayList<>();
        recyclerViewStew = rootView.findViewById(R.id.recycler_stew);


        recyclerViewStew.setHasFixedSize(true);
        layoutManagerStew = new LinearLayoutManager(getActivity());
        adapterStew = new CustomRecyclerAdapter(getActivity(),stewList);
        recyclerViewStew.setLayoutManager(layoutManagerStew);
        initStew(stewList);

        //CustomRecyclerAdapter customRecyclerAdapter = new CustomRecyclerAdapter(stewList,FoodCategoryList.STEW);
        recyclerViewStew.setAdapter(adapterStew);



        return rootView;
    }

    private void initStew(ArrayList stewList) {
        stewList.add(new ListItem(R.drawable.stew_peacock, FoodCategoryList.STEW,
                "[피코크] 진한 육개장 500g",
                "피코크 진한 육개장은 두 식구가 먹기에 딱 적절한 양입니다. 저는 신혼부부인데 남편이랑 주말에 점심으로 해 먹었습니다. (해 먹었다기엔 너무 거창하고 그냥 데워 먹은거네요..) 1~2인분이라고 되어있는데 햇반이랑 먹으면 2인이 적당히 배부르게 잘 먹을 수 있을 것 같습니다.",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        stewList.add(new ListItem(R.drawable.stew_pulmuone, FoodCategoryList.STEW,
                "풀무원 정통순두부찌개 Kit 602g",
                "집에서 순두부찌개하기 어려웠는데 양념장다들어있어서 따로 간을 안해도되네요 남편이 제가한줄알고 놀래더군요 ㅎㅎㅎ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        stewList.add(new ListItem(R.drawable.stew_bibigo, FoodCategoryList.STEW,
                "CJ 비비고 차돌육개장 500g",
                "일반 육개장보다 밍밍하니 별루에요 그냥 비비고 육개장 드시는게 훨씬 맛잇어요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        stewList.add(new ListItem(R.drawable.stew_odduggi, FoodCategoryList.STEW,
                "오뚜기 간편 미역국 5입(9.5g*5)",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
    }


}
