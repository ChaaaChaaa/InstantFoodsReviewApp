package com.myapp.instantfoodsreviewapp.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.instantfoodsreviewapp.R;
import com.myapp.instantfoodsreviewapp.databinding.FragmentHomeBinding;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDdokbokkiFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListDumplingFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListFriedRiceFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListNoodleFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListPizzaFragment;
import com.myapp.instantfoodsreviewapp.fragment.product.ProductListStewFragment;

import org.jetbrains.annotations.NotNull;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private FragmentHomeBinding fragmentHomeBinding;
    private View.OnClickListener onClickListener;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View rootView = fragmentHomeBinding.getRoot();
        rootView.setOnClickListener(this);
        init();
        onClick(rootView);
        return rootView;
    }

    private void init() {
        ImageView image_coldNoodles = fragmentHomeBinding.imageColdNoodle;
        ImageView image_ddokbokki = fragmentHomeBinding.imageDdokbokki;
        ImageView image_dumpling = fragmentHomeBinding.imageDumpling;
        ImageView image_friedRice = fragmentHomeBinding.imageFriedRice;
        ImageView image_pizza = fragmentHomeBinding.imagePizza;
        ImageView image_stew = fragmentHomeBinding.imageStew;

        image_coldNoodles.setOnClickListener(this);
        image_ddokbokki.setOnClickListener(this);
        image_dumpling.setOnClickListener(this);
        image_friedRice.setOnClickListener(this);
        image_pizza.setOnClickListener(this);
        image_stew.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentHomeBinding.imageColdNoodle){
            FragmentTransaction fragmentTransactionNoodle = getFragmentManager().beginTransaction();
            fragmentTransactionNoodle.replace(R.id.fragment_container, new ProductListNoodleFragment());
            fragmentTransactionNoodle.addToBackStack(null);
            fragmentTransactionNoodle.commit();
        }

        else if(view == fragmentHomeBinding.imageDdokbokki) {
            FragmentTransaction fragmentTransactionddok = getFragmentManager().beginTransaction();
            fragmentTransactionddok.replace(R.id.fragment_container, new ProductListDdokbokkiFragment());
            fragmentTransactionddok.addToBackStack(null);
            fragmentTransactionddok.commit();
        }

        else if(view == fragmentHomeBinding.imageDumpling) {
            FragmentTransaction fragmentTransactionDumpling = getFragmentManager().beginTransaction();
            fragmentTransactionDumpling.replace(R.id.fragment_container, new ProductListDumplingFragment());
            fragmentTransactionDumpling.addToBackStack(null);
            fragmentTransactionDumpling.commit();
        }

        else if(view == fragmentHomeBinding.imageFriedRice) {
            FragmentTransaction fragmentTransactionFriedRice = getFragmentManager().beginTransaction();
            fragmentTransactionFriedRice.replace(R.id.fragment_container, new ProductListFriedRiceFragment());
            fragmentTransactionFriedRice.addToBackStack(null);
            fragmentTransactionFriedRice.commit();
        }

        else if(view == fragmentHomeBinding.imagePizza) {
            FragmentTransaction fragmentTransactionPizza = getFragmentManager().beginTransaction();
            fragmentTransactionPizza.replace(R.id.fragment_container, new ProductListPizzaFragment());
            fragmentTransactionPizza.addToBackStack(null);
            fragmentTransactionPizza.commit();
        }

        else if(view == fragmentHomeBinding.imageStew) {
            FragmentTransaction fragmentTransactionStew = getFragmentManager().beginTransaction();
            fragmentTransactionStew.replace(R.id.fragment_container, new ProductListStewFragment());
            fragmentTransactionStew.addToBackStack(null);
            fragmentTransactionStew.commit();
        }
    }
}
