//package com.myapp.instantfoodsreviewapp.restapi;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.util.HashSet;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class AuthInterceptor implements Interceptor {
//    @NotNull
//    @Override
//    public Response intercept(@NotNull Chain chain) throws IOException {
//        Response originalResponse = chain.proceed(chain.request());
//        if(!originalResponse.header("Set-Cookie").isEmpty()){
//            HashSet<String> cookies = new HashSet<>();
//            for(String header : originalResponse.header("Set-Cookie")){
//                cookies.add(header);
//                if(header.startsWith("XSRF-TOKEN")){
//                    String newCookie[] = header.split(";");
//                    System.out.println("newCookie Length: "+newCookie.length);
//                    for(String ss:newCookie){
//                        if(ss.startsWith("XSRF-TOKEN")){
//                            System.out.println("Cookies ss: "+ss);
//                            sharedPrefs.setToken(ss);
//                        }
//                    }
//                }
//            }
//        }
//        return originalResponse;
//    }
//}
