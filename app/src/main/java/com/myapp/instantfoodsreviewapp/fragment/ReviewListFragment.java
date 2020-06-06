package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ReviewData;

import java.util.List;


public class ReviewListFragment extends Fragment {

   private RecyclerView reviewRecyclerView;
   private ReviewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list,container,false);
        reviewRecyclerView = view.findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI(){
        ReviewLab reviewLab = ReviewLab.get(getActivity());
        List<ReviewData> reviewDataList = reviewLab.getReviews();
        reviewAdapter = new ReviewAdapter(reviewDataList);
        reviewRecyclerView.setAdapter(reviewAdapter);

    }

    private class ReviewHolder extends RecyclerView.ViewHolder{
        private ReviewData reviewData;
        private TextView goodPointReview;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            goodPointReview = itemView.findViewById(R.id.detail_review_good);
        }

        public void bindReview(ReviewData reviewData){
            this.reviewData = reviewData;
            goodPointReview.setText(reviewData.getGoodPointReview());
        }
    }

    private class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder>{
        private List<ReviewData> reviewDataList;
        public ReviewAdapter(List<ReviewData> reviewDataList){
            this.reviewDataList = reviewDataList;
        }

        @NonNull
        @Override
        public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new ReviewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
            ReviewData reviewData = reviewDataList.get(position);
            holder.goodPointReview.setText(reviewData.getGoodPointReview());
        }

        @Override
        public int getItemCount() {
            return reviewDataList.size();
        }
    }
}
