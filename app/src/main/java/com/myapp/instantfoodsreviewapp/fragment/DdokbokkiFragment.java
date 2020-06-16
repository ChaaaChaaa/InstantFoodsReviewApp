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

public class DdokbokkiFragment extends Fragment {
    private RecyclerView recyclerViewDdokbokki;
    private CustomRecyclerAdapter adapterDdokbokki;
    private LinearLayoutManager layoutManagerDdokbokki;
    private FoodCategoryList foodCategoryList;
    private ArrayList<ListItem> ddokbokkiList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ddokbokki, container, false);
        // View rootView = inflater.inflate(R.layout.fragment_ddokbokki, null);
        setHasOptionsMenu(true);
        recyclerViewDdokbokki = rootView.findViewById(R.id.recycler_ddokbokki);
        recyclerViewDdokbokki.setHasFixedSize(true);
        layoutManagerDdokbokki = new LinearLayoutManager(getActivity());
        recyclerViewDdokbokki.setLayoutManager(layoutManagerDdokbokki);
        initDdokbokki();
        showRecyclerView();
        return rootView;
    }

    private void showRecyclerView() {
        adapterDdokbokki = new CustomRecyclerAdapter(getActivity(), ddokbokkiList);
        recyclerViewDdokbokki.setAdapter(adapterDdokbokki);
    }

    private void initDdokbokki() {

        ddokbokkiList.add(new ListItem(R.drawable.ddok_oddukki, foodCategoryList.DDOKBOKKI,
                "CJ 미정당 매콤까르보나라 누들떡볶이400g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_full));

        ddokbokkiList.add(new ListItem(R.drawable.ddok_peacock, foodCategoryList.DDOKBOKKI,
                "피콕분식 신당동식떡볶이 970g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_full,
                R.drawable.ic_star_half));

        ddokbokkiList.add(new ListItem(R.drawable.ddok_dongwon, foodCategoryList.DDOKBOKKI,
                "동원 떡볶이의신 신당동 즉석쫄볶이397g",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
                R.drawable.ic_star_full,
                R.drawable.ic_star_blank,
                R.drawable.ic_star_blank));

        ddokbokkiList.add(new ListItem(R.drawable.ddok_pulmuone, foodCategoryList.DDOKBOKKI,
                "[풀무원] 순쌀 떡볶이 480g (2인분)",
                "Compiler allocated 4MB to compile void android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int)",
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
                if (adapterDdokbokki != null) {
                    adapterDdokbokki.getFilter().filter(newText);
                }

                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(searchMenu, inflater);
    }
}
