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
public class NoodleFragment extends Fragment {
    private ArrayList<ListItem> noodleList = new ArrayList<>();
    private FoodCategoryList foodCategoryList;

    private RecyclerView recyclerViewNoodle;
    private RecyclerView.Adapter adapterNoodle;
    private LinearLayoutManager linearLayoutManagerNoodle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noodle, container, false);
        recyclerViewNoodle = rootView.findViewById(R.id.recycler_noodle);
        inputListData();
        adapterNoodle = new CustomRecyclerAdapter(getActivity(),noodleList);
        linearLayoutManagerNoodle = new LinearLayoutManager(getActivity());

        recyclerViewNoodle.setAdapter(adapterNoodle);
        recyclerViewNoodle.setLayoutManager(linearLayoutManagerNoodle);

        return rootView;
    }

    private void inputListData(){

        noodleList.add(new ListItem(R.drawable.noodle_naturevil,foodCategoryList.DDOKBOKKI,
                "[한끼든든쌀국수] 우리쌀 97.7% * 10개(멸치맛)",
                "그동안 먹었던 쌀국수들은 쌀 함량이 50% 정도였는데 이건 97%이고 글루텐도 없어서 먹고나면 속이 편안해요~ 푹 퍼지지도 않고 정말 맛았네요~ 다른 쌀국수보다 가격이 조금 더 나가지만 그럴만한 제품이에요~!",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        noodleList.add(new ListItem(R.drawable.noodle_cj,foodCategoryList.DDOKBOKKI,
                "CJ 동치미 물냉면 4인(1,816g)",
                "깊고 시원한 맛이 일품인 동치미 물냉면 우리가족 모두가 좋아하는 냉면~꼭 여름이 아니더라도 겨울에도 항상 찾는 냉면이지요 냉면집에서 파는 냉면도 맛있지만 이렇게 집에서 만들어 먹는 냉면 또한 너무 매력있는 것 같아요 cj동치미 물냉면은 4인분으로 가격도 괜찮고 양도 꽤 괜찮아요",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        noodleList.add(new ListItem(R.drawable.noodle_pulmuone,foodCategoryList.DDOKBOKKI,
                "[풀무원] 생가득 함흥 비빔냉면 460G",
                "제가 냉면 덕후라 유명 브랜드 냉면 다 먹어봤는데 이게 젤 맛있어요! 열무나 오이 추가하셔서 드셔도 맛있고 목살 구워서 같이 싸드시면 끝나여 ㅎ",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        noodleList.add(new ListItem(R.drawable.noodle_odduggi,foodCategoryList.DDOKBOKKI,
                "오뚜기 평양물냉면 2인",
                "시원하고 새콤달콤하네요 무난하게 맛있어요~",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
    }
}
