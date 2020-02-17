package com.vargancys.vargancysnews.module.content.news.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2020/02/17
 * version:1.0
 */
public interface NewsService {
    @GET()
    Call<NewsDataInfo> getNewsDataInfo();
}
