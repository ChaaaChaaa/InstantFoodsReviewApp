package com.myapp.instantfoodsreviewapp.restapi;

import com.myapp.instantfoodsreviewapp.model.UserAccountData;
import com.myapp.instantfoodsreviewapp.model.UserLoginData;
import com.myapp.instantfoodsreviewapp.model.UserRegisterData;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("v1/user/regist")
    Call<UserRegisterData> regist(@Field("email") String email, @Field("nickname") String nickname, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/user/login")
    Call<ApiResultDto> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
   // @Header("key : authorization","token")
    @GET("v1/user/account")
    Call<UserAccountData> account(@Header("authorization") String token, @Field("email") String email, @Field("nickname") String nickname);
}
