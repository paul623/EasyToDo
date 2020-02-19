package com.paul.easytodo.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Retrofit工厂
 * 用来生成Retrofit对象
 * IP 116.62.143.53
 * 端口号 8889
 * */
public class RetrofitFactory {
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://116.62.143.53:8889")
            .addConverterFactory(GsonConverterFactory.create())//增加一个转换工厂
            .build();
    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
