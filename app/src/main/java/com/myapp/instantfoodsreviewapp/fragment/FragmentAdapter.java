package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.instantfoodsreviewapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdapter extends Fragment {


    public FragmentAdapter() {
        // Required empty public constructor
    }

    private class FragmentHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;

        public FragmentHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}
