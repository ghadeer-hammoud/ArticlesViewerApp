package com.example.articlesviewerapp.di;

import com.example.articlesviewerapp.ui.ArticlesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelModule.class, MainModule.class}
    )
    abstract ArticlesActivity contributeArticlesActivity();

}
