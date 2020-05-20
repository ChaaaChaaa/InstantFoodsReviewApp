package com.myapp.instantfoodsreviewapp;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.instantfoodsreviewapp.databinding.FragmentEmailLoginBinding;


public class EmailLoginFragment extends Fragment {

    public EmailLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEmailLoginBinding fragmentEmailLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_email_login, container, false);
        View view = fragmentEmailLoginBinding.getRoot();
        return view;
        //return inflater.inflate(R.layout.activity_main, container, false);
    }
}
