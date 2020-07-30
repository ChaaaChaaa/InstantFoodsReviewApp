package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.instantfoodsreviewapp.R;


public class WriteReviewFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerFoodCategory;
    private EditText textViewReviewTitle;
    private RatingBar ratingBarReview;
    private EditText editTextGoodPoint;
    private EditText editTextBadPoint;
    private ImageView reviewPictureFirst;
    private ImageView reviewPictureSecond;


    //ratingRatingBar.getRating()
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_review, container, false);
        init(rootView);
        setSpinner();
        return rootView;
    }

    private void init(View rootView) {
        spinnerFoodCategory = rootView.findViewById(R.id.spn_food_category);
        textViewReviewTitle = rootView.findViewById(R.id.tv_review_title);
        ratingBarReview = rootView.findViewById(R.id.review_rating_bar);
        editTextBadPoint = rootView.findViewById(R.id.et_bad_point_review);
        editTextGoodPoint = rootView.findViewById(R.id.et_good_point_review);
        reviewPictureFirst = rootView.findViewById(R.id.iv_review_image_first);
        reviewPictureSecond = rootView.findViewById(R.id.iv_review_image_second);
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.foods_category_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoodCategory.setAdapter(arrayAdapter);
        spinnerFoodCategory.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectItem = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selectItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "nothing selected!", Toast.LENGTH_SHORT).show();
    }

    private void setWrite() {
        String reviewTitle = textViewReviewTitle.getText().toString();
        String goodPoint = editTextGoodPoint.getText().toString();
        String badPoint = editTextBadPoint.getText().toString();
    }




}
