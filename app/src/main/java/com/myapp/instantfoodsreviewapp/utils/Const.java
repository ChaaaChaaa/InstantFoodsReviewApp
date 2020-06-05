package com.myapp.instantfoodsreviewapp.utils;


public class Const {
    public static boolean isNullOrEmptyString( String... msgs) {
        for (String item : msgs) {
            if (item == null || item.isEmpty()) {
<<<<<<< HEAD
                return false; //낫널일땐 펄스로 수정함
            }
        }
        return true; // 이즈낫널이니까, 낫널이 아닐땐 트루
=======
                return false;
            }
        }
        return true;
>>>>>>> [UPDATE] to use token in main activity
    }

    //model : 이메일 형식 체크
    // null empty 공통으로 util로


}
