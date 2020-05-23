package com.myapp.instantfoodsreviewapp.restapi;

import android.util.Log;

import org.jetbrains.annotations.NotNull;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitInterface buildHTTPClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mogacko.kro.kr")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit.create(RetrofitInterface.class);
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {
                Log.d("HTTP", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public static RetrofitInterface getRestMethods(){
        return buildHTTPClient();
    }
}
