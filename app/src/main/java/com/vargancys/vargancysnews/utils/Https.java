package com.vargancys.vargancysnews.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/14
 * version:1.0
 */
public class Https {
    private static <T> T createAPI(Class<T> clazz, String baseUri){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUri)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
