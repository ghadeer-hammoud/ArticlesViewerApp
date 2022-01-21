package com.example.articlesviewerapp.viewmodels;

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

public class ArticlesViewModel extends ViewModel {

    private ArticlesRepository articlesRepository;
    private MediatorLiveData<Resource<List<Article>>> resourceArticlesLiveData = new MediatorLiveData<>();

    @Inject
    public ArticlesViewModel(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public void loadMostPopularArticles(){
        resourceArticlesLiveData.setValue(Resource.loading(null));
        final LiveData<Resource<List<Article>>> source = LiveDataReactiveStreams.fromPublisher(
                articlesRepository.getMostPopularArticles()
        );

        resourceArticlesLiveData.addSource(source, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                resourceArticlesLiveData.setValue(listResource);
                resourceArticlesLiveData.removeSource(source);
            }
        });
    }


    public LiveData<Resource<List<Article>>> observeArticles(){
        loadMostPopularArticles();
        return resourceArticlesLiveData;
    }
}
