package com.myapp.instantfoodsreviewapp.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new StewFragment();
            case 1:
                return new NoodleFragment();
            case 2:
                return new DdokbokkiFragment();
            case 3:
                return new DumplingFragment();
            case 4:
                return new FriedRiceFragment();
            case 5:
                return new PizzaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
