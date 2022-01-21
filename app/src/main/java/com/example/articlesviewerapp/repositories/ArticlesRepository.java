package com.example.articlesviewerapp.repositories;

import android.util.Log;

import com.example.articlesviewerapp.Resource;
import com.example.articlesviewerapp.models.Article;
import com.example.articlesviewerapp.models.ArticlesApiResponse;
import com.example.articlesviewerapp.network.ArticlesApi;
import com.example.articlesviewerapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class ArticlesRepository {

    private ArticlesApi articlesApi;
    private List<Article> articles = new ArrayList<>();


    @Inject
    public ArticlesRepository(ArticlesApi articlesApi) {
        this.articlesApi = articlesApi;
    }

    public Flowable<Resource<List<Article>>> getMostPopularArticles(){
        return articlesApi.getMostPopularArticles(Constants.API_KEY)

                .onErrorReturn(new Function<Throwable, ArticlesApiResponse>() {
                    @NonNull
                    @Override
                    public ArticlesApiResponse apply(@NonNull Throwable throwable) {
                        ArticlesApiResponse articlesApiResponse =  new ArticlesApiResponse();
                        articlesApiResponse.setStatus("error: " + throwable.getMessage());
                        return articlesApiResponse;
                    }
                })

                .map(new Function<ArticlesApiResponse, Resource<List<Article>>>() {
                    @NonNull
                    @Override
                    public Resource<List<Article>> apply(@NonNull ArticlesApiResponse articlesApiResponse) throws Exception {
                        if(articlesApiResponse.getStatus().startsWith("error")){
                            return Resource.error(articlesApiResponse.getStatus(), null);
                        }

                        else{
                            articles = articlesApiResponse.getResults();
                            return Resource.success(articles);
                        }

                    }
                })

                .subscribeOn(Schedulers.io());
    }

    public Resource<Article> getArticleDetails(long articleId){

        for (Article article : articles){
            if(article.getId() == articleId)
                return Resource.success(article);
        }
        return Resource.error("Couldn't find article with Id = " + articleId, null);
    }
}
