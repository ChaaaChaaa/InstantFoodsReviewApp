package com.myapp.instantfoodsreviewapp.restapi;


import com.myapp.instantfoodsreviewapp.model.PostResponse;
import com.myapp.instantfoodsreviewapp.model.PostsResponse;
import com.myapp.instantfoodsreviewapp.model.UserRegisterData;
import com.myapp.instantfoodsreviewapp.model.entity.AccountDto;
import com.myapp.instantfoodsreviewapp.model.entity.ApiResultDto;
import com.myapp.instantfoodsreviewapp.model.ProductResponse;
import com.myapp.instantfoodsreviewapp.model.entity.ProductListDto;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("v1/user/regist")
    Call<UserRegisterData> regist(@Field("email") String email, @Field("nickname") String nickname, @Field("password") String password);

    @FormUrlEncoded
    @POST("v1/user/login")
    Call<ApiResultDto> login(@Field("email") String email, @Field("password") String password);


    // @Header("key : authorization","token")

    @GET("v1/user/account")
        //Call<UserAccountData> account();
    Call<AccountDto> account(@Header("authorization") String token);

    @FormUrlEncoded
    @PUT("v1/user/change/pwd")
    Call<ApiResultDto> change(@Header("authorization") String token,
                              @Field("original_password") String originPassword,
                              @Field("request_password") String requestPassword);


    @POST("v1/user/secession")
    Call<ApiResultDto> secession(@Header("authorization") String token);


    @FormUrlEncoded
    @PUT("v1/user/nickname")
    Call<ApiResultDto> nickname(@Header("authorization") String token,
                                @Field("nickname") String newNickName);

    @Multipart
    @POST("v1/user/pimage")
    Call<ApiResultDto>  pimage(@Header("authorization") String token,
                               @Part MultipartBody.Part file1,
                               @Part MultipartBody.Part file2);

    @GET("v1/product/list/{category_id}")
    Call<ProductResponse> list (@Path("category_id") int categoryId,
                               @Query("size") int size,
                               @Query("page") int page);
    @GET("v1/post/posts")
    Call<PostsResponse> posts(@Header("authorization") String token,
                              @Query("page") int page,
                              @Query("date") long date,
                              @Query("size") int size);

    @GET("v1/post/search/product")
    Call<PostsResponse> searchProduct(@Header("authorization") String token,
                              @Query("pr_id") int pr_id,
                              @Query("sort_type") String sortType,
                              @Query("page") int page,
                              @Query("date") long date,
                              @Query("size") int size);




    @Multipart
    @POST("v1/post/upload")
    Call<PostResponse> upload(@Header("authorization") String token,
                              @Part("title") String title,
                              @Part("good_contents") String good_contents,
                              @Part("bad_contents") String bad_contents,
                              @Part("score") float score,
                              @Part("pr_id") Integer pr_id,
                              @Part MultipartBody.Part file1,
                              @Part MultipartBody.Part file2
                              );
    @GET("v1/post/search")
    Call<PostsResponse>search(@Header("authorization") String token,
                 @Query("page") int page,
                 @Query("date") int date,
                 @Query("size") int size,
                 @Query("keyword") String keyword);



}