package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.PostViewModel;
import com.myapp.instantfoodsreviewapp.adapter.PostsRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.model.Post;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class PostsListFragment extends Fragment {
    private RecyclerView recyclerViewPostsList;
    private PostsRecyclerAdapter postsRecyclerAdapter;
    private LinearLayoutManager layoutManagerPostsList;
    private List<Product> pickProduct = new ArrayList<>();

    public PostsListFragment(List<Product> pickProduct){
        this.pickProduct = pickProduct;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list,container,false);
        recyclerViewPostsList = rootView.findViewById(R.id.recycler_posts_list);
        layoutManagerPostsList = new LinearLayoutManager(getActivity());
        recyclerViewPostsList.setLayoutManager(layoutManagerPostsList);
        recyclerViewPostsList.setHasFixedSize(true);
        initPostsList();
        return rootView;
    }



    private void initPostsList(){
        PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postsRecyclerAdapter = new PostsRecyclerAdapter(pickProduct);
        postViewModel.postPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(PagedList<Post> posts) {
                postsRecyclerAdapter.submitList(posts);
            }
        });
        recyclerViewPostsList.setAdapter(postsRecyclerAdapter);
    }
}
