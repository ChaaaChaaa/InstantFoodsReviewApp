package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.DdokbokkiAdapter;
import com.myapp.instantfoodsreviewapp.model.ListItem;

import java.util.ArrayList;

public class DdokbokkiFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ListItem> listItems = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ddokbokki, container, false);
        inputListData();
        recyclerView = view.findViewById(R.id.recycler_ddokbokki);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new DdokbokkiAdapter(listItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void inputListData(){

        listItems.add(new ListItem(R.drawable.ddok_oddukki,"떡볶이",
                "CJ 미정당 매콤까르보나라 누들떡볶이400g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        listItems.add(new ListItem(R.drawable.ddok_peacock,"떡볶이",
                "피콕분식 신당동식떡볶이 970g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        listItems.add(new ListItem(R.drawable.ddok_dongwon,"떡볶이",
                "동원 떡볶이의신 신당동 즉석쫄볶이397g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        listItems.add(new ListItem(R.drawable.ddok_pulmuone,"떡볶이",
                "[풀무원] 순쌀 떡볶이 480g (2인분)",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));
    }
}