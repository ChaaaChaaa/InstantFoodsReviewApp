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
                .baseUrl("http://www.mogacko.kro.kr/")
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
                //.addInterceptor(new BasicAuthInterceptor())
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

    public static RetrofitInterface getRestMethods() {
        return buildHTTPClient();
    }
//
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

//    public void sendNetworkRequest(String token){
//        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
//        okhttpBuilder.addInterceptor(new Interceptor() {
//            @NotNull
//            @Override
//            public Response intercept(@NotNull Chain chain) throws IOException {
//                Request request = chain.request();
//                Request.Builder newRequest = request.newBuilder().header("Authorization","secret-key");
//                return chain.proceed(newRequest.build());
//            }
//        });
//    }
}
