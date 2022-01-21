package com.example.articlesviewerapp.di;


import com.example.articlesviewerapp.viewmodels.ArticleDetailsViewModel;
import com.example.articlesviewerapp.viewmodels.ArticlesViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel.class)
    public abstract ViewModel bindArticlesViewModel(ArticlesViewModel articlesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailsViewModel.class)
    public abstract ViewModel bindArticleDetailsViewModel(ArticleDetailsViewModel articleDetailsViewModel);
}
