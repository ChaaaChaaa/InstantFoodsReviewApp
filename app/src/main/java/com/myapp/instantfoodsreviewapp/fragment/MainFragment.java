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

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;

    }

    private class FragmentHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;

        public FragmentHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView;
        }
    }

    private class FragmentAdapter extends RecyclerView.Adapter<FragmentHolder>{

        @NonNull
        @Override
        public FragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
