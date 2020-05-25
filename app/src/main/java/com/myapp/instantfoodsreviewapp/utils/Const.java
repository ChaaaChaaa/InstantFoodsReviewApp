package com.myapp.instantfoodsreviewapp.utils;

import com.google.android.material.textfield.TextInputLayout;

public class Const {
    public static boolean isNullOrEmptyString( String... msgs) {
        for (String item : msgs) {
            if (item == null || item.isEmpty()) {
                return true;
            }
        }
        return false;
    }


}
