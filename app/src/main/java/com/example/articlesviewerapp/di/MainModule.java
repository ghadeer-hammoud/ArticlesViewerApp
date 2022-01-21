package com.example.articlesviewerapp.di;

import com.example.articlesviewerapp.network.ArticlesApi;
import com.example.articlesviewerapp.repositories.ArticlesRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static ArticlesApi provideArticlesApi(Retrofit retrofit){
        return retrofit.create(ArticlesApi.class);
    }

    @MainScope
    @Provides
    static ArticlesRepository provideArticlesRepository(ArticlesApi articlesApi){
        return new ArticlesRepository(articlesApi);
    }

}
