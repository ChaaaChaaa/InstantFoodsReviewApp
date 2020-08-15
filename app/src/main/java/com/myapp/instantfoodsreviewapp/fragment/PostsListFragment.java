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

public class PostsListFragment extends Fragment {
    private RecyclerView recyclerViewPostsList;
    private PostsRecyclerAdapter postsRecyclerAdapter;
    private LinearLayoutManager layoutManagerPostsList;
    private int productId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list,container,false);
//        recyclerViewPostsList.setHasFixedSize(true);
        recyclerViewPostsList = rootView.findViewById(R.id.recycler_posts_list);
        layoutManagerPostsList = new LinearLayoutManager(getActivity());
        recyclerViewPostsList.setLayoutManager(layoutManagerPostsList);
        getBundleInfo();
        initPostsList();
        return rootView;
    }

    private void  getBundleInfo(){
        Bundle bundle = getArguments();
        int getProductIdResult = bundle.getInt("productID");
        productId = getProductIdResult;
    }

    private void initPostsList(){
        PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postsRecyclerAdapter = new PostsRecyclerAdapter(productId);
        postViewModel.postPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(PagedList<Post> posts) {
                postsRecyclerAdapter.submitList(posts);
            }
        });
        recyclerViewPostsList.setAdapter(postsRecyclerAdapter);

    }

}
