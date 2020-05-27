package com.myapp.instantfoodsreviewapp.restapi;

import android.util.Log;

import com.myapp.instantfoodsreviewapp.BuildConfig;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitInterface buildHTTPClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mogacko.kro.kr/")
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

//    Interceptor headerAuthorizationInterceptor = new Interceptor() {
//        @NotNull
//        @Override
//        public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
//            okhttp3.Request request = chain.request();
//            Headers headers = request.headers().newBuilder().add("Authorization",authToken).build();
//            request = request.newBuilder().headers(headers).build();
//            return chain.proceed(request);
//        }
//    };
}
