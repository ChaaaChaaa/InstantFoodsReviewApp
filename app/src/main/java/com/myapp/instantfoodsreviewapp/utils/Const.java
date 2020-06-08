package com.myapp.instantfoodsreviewapp.utils;


public class Const {
    public static boolean isNullOrEmptyString( String... msgs) {
        for (String item : msgs) {
            if (item == null || item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //model : 이메일 형식 체크
    // null empty 공통으로 util로


}