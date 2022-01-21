package com.example.articlesviewerapp.di;

import com.example.articlesviewerapp.ui.ArticleDetailFragment;
import com.example.articlesviewerapp.ui.ArticlesListFragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ArticlesListFragment contributeArticlesListFragment();

    @ContributesAndroidInjector
    abstract ArticleDetailFragment contributeArticleDetailFragment();
}
