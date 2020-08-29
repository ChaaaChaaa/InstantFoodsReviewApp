package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.PostViewModel;
import com.myapp.instantfoodsreviewapp.adapter.PostsRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.model.Posts;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class PostsListFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerViewPostsList;
    private PostsRecyclerAdapter postsRecyclerAdapter;
    private LinearLayoutManager layoutManagerPostsList;
    private List<Product> pickProduct = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private PostViewModel postViewModel;

    public PostsListFragment(List<Product> pickProduct) {
        this.pickProduct = pickProduct;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list, container, false);
        floatingActionButton = rootView.findViewById(R.id.btn_post_floating);
        recyclerViewPostsList = rootView.findViewById(R.id.recycler_posts_list);
        layoutManagerPostsList = new LinearLayoutManager(getActivity());
        recyclerViewPostsList.setLayoutManager(layoutManagerPostsList);
        recyclerViewPostsList.setHasFixedSize(true);
        initPostsList();
        floatingActionButton.setOnClickListener(this);
        return rootView;
    }


    private void initPostsList() {
        PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postsRecyclerAdapter = new PostsRecyclerAdapter(pickProduct);
        postViewModel.postPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Posts>>() {
            @Override
            public void onChanged(PagedList<Posts> posts) {
                postsRecyclerAdapter.submitList(posts);
            }
        });
        recyclerViewPostsList.setAdapter(postsRecyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = new WritePostFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        int ProductID = pickProduct.get(0).getPrId();
        String productName = pickProduct.get(0).getPrTitle();
        bundle.putInt("ProductID", ProductID);
        bundle.putString("ProductName",productName);
        fragment.setArguments(bundle);
    }

    private void performSearch(String searchKey){
        postViewModel.filterTextAll.setValue(searchKey);
        postViewModel.postPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<Posts>>() {
            @Override
            public void onChanged(PagedList<Posts> posts) {
                postsRecyclerAdapter.submitList(posts);
            }
        });
        recyclerViewPostsList.setAdapter(postsRecyclerAdapter);
    }
}
