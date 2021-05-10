package com.myapp.instantfoodsreviewapp.restapi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitInterface buildHTTPClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ppizil.kro.kr/review/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit.create(RetrofitInterface.class);
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor headerInterceptor = new HttpLoggingInterceptor();
        headerInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        HttpLoggingInterceptor bodyInterceptor = new HttpLoggingInterceptor();
        bodyInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(bodyInterceptor)
                .build();
    }

    public static RetrofitInterface getRestMethods() {
        return buildHTTPClient();
    }
}