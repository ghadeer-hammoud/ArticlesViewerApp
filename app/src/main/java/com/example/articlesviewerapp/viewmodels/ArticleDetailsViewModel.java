package com.example.articlesviewerapp.viewmodels;

import android.util.Log;

import com.example.articlesviewerapp.Resource;
import com.example.articlesviewerapp.models.Article;
import com.example.articlesviewerapp.repositories.ArticlesRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ArticleDetailsViewModel extends ViewModel {

    private ArticlesRepository articlesRepository;
    private MediatorLiveData<Resource<Article>> resourceArticleLiveData = new MediatorLiveData<>();

    @Inject
    public ArticleDetailsViewModel(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
        Log.d("ghadeer", "ArticleDetailsViewModel: ViewModel Created");
    }


    public void loadArticleDetails(long articleId){
        resourceArticleLiveData.setValue(Resource.loading(null));
        resourceArticleLiveData.setValue(articlesRepository.getArticleDetails(articleId));
    }


    public LiveData<Resource<Article>> observeArticle(long articleId){
        loadArticleDetails(articleId);
        return resourceArticleLiveData;
    }
}
