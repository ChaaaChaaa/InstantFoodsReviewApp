package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.model.ReviewData;

public class ReviewFragment extends Fragment {
    private ReviewData reviewData;
    private EditText goodReviewField;
    private EditText badReviewField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reviewData = new ReviewData();
    }

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        goodReviewField = view.findViewById(R.id.detail_review_good);
        goodReviewField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                goodReviewField.setText(toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        badReviewField = view.findViewById(R.id.detail_review_bad);
        badReviewField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                badReviewField.setText(toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}
