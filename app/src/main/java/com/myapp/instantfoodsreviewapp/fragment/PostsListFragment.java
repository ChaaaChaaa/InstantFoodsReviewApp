package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.adapter.PostViewModel;
import com.myapp.instantfoodsreviewapp.adapter.PostViewModelFactory;
import com.myapp.instantfoodsreviewapp.adapter.PostsRecyclerAdapter;
import com.myapp.instantfoodsreviewapp.model.Product;

import java.util.List;

public class PostsListFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerViewPostsList;
    private PostsRecyclerAdapter postsRecyclerAdapter;
    private final List<Product> pickProduct;
    private final int productId;
    PostViewModel postViewModel;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    public PostsListFragment(List<Product> pickProduct) {
        this.pickProduct = pickProduct;
        productId = pickProduct.get(0).getPrId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts_list, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.post_swipe_refresh);
        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.btn_post_floating);
        recyclerViewPostsList = rootView.findViewById(R.id.recycler_posts_list);
        progressBar = rootView.findViewById(R.id.loadmore_progress);
        swipeRefreshLayout.setOnRefreshListener(() -> postViewModel.refresh());
        LinearLayoutManager layoutManagerPostsList = new LinearLayoutManager(getActivity());
        recyclerViewPostsList.setLayoutManager(layoutManagerPostsList);
        recyclerViewPostsList.setHasFixedSize(true);
        initPostsList();
        floatingActionButton.setOnClickListener(this);
        return rootView;
    }

    private void initPostsList() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, new PostViewModelFactory(productId));
        postViewModel = viewModelProvider.get(PostViewModel.class);
        postsRecyclerAdapter = new PostsRecyclerAdapter(pickProduct);
        postViewModel.postPagedList.observe(getViewLifecycleOwner(), posts -> {
            postsRecyclerAdapter.submitList(posts);
            swipeRefreshLayout.setRefreshing(false);
        });
        swipeRefreshLayout.setOnRefreshListener(() -> postViewModel.refresh());
        recyclerViewPostsList.setAdapter(postsRecyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = new WritePostFragment();
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        int ProductID = pickProduct.get(0).getPrId();
        String productName = pickProduct.get(0).getPrTitle();
        bundle.putInt("ProductID", ProductID);
        bundle.putString("ProductName", productName);
        fragment.setArguments(bundle);
    }
}
