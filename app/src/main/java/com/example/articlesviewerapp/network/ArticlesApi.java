package com.example.articlesviewerapp.network;

import com.example.articlesviewerapp.models.ArticlesApiResponse;
import com.example.articlesviewerapp.utils.Constants;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticlesApi {

    @GET("mostviewed/all-sections/7.json")
    Flowable<ArticlesApiResponse> getMostPopularArticles(@Query("api-key") String app_key);
}