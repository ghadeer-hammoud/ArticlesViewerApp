package com.example.articlesviewerapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.articlesviewerapp.R;
import com.example.articlesviewerapp.Resource;
import com.example.articlesviewerapp.databinding.FragmentArticleDetailBinding;
import com.example.articlesviewerapp.di.ViewModelProviderFactory;
import com.example.articlesviewerapp.models.Article;
import com.example.articlesviewerapp.viewmodels.ArticleDetailsViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import javax.inject.Inject;

public class ArticleDetailFragment extends DaggerFragment {

    public static final String ARG_ARTICLE_ID = "article_id";

    private CollapsingToolbarLayout mToolbarLayout;
    private ImageView ivImage;
    private TextView tvTitle, tvType, tvSection, tvByline, tvPublishedDate, tvUpdated, tvAbstract, tvSource, tvSeeOriginal;

    private FragmentArticleDetailBinding binding;

    private ArticleDetailsViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    RequestManager glideInstance;


    public ArticleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentArticleDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        ivImage = binding.ivImage;
        tvTitle = binding.tvTitle;
        tvType = binding.tvType;
        tvSection = binding.tvSection;
        tvByline = binding.tvByline;
        tvPublishedDate = binding.tvPublishedDate;
        tvUpdated = binding.tvUpdated;
        tvAbstract = binding.tvAbstract;
        tvSource = binding.tvSource;
        tvSeeOriginal = binding.tvSeeOriginal;


        viewModel = ViewModelProviders.of(this, providerFactory).get(ArticleDetailsViewModel.class);
        if (getArguments().containsKey(ARG_ARTICLE_ID)) {
            subscribeObservers();
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void subscribeObservers(){

        viewModel.observeArticle(Long.parseLong(getArguments().getString(ARG_ARTICLE_ID))).observe(getViewLifecycleOwner(), new Observer<Resource<Article>>() {
            @Override
            public void onChanged(Resource<Article> articleResource) {

                if(articleResource != null){
                    switch (articleResource.status){
                        case LOADING:{
                            //showProgressBar();
                            break;
                        }
                        case SUCCESS:{
                            //hideProgressBar();
                            populateData(articleResource.data);

                            break;
                        }
                        case ERROR:{
                            //hideProgressBar();
                            //mTextView.setText(articleResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void populateData(Article article){

        mToolbarLayout.setTitle(article.getTitle());
        tvTitle.setText(article.getTitle());
        tvType.setText(article.getType());
        tvSection.setText(article.getSection());
        tvByline.setText(article.getByline());
        tvPublishedDate.setText(article.getPublishedDate());
        tvUpdated.setText(article.getUpdated());
        tvAbstract.setText(article.getAbstract());
        tvSource.setText(article.getSource() +".");
        tvSeeOriginal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });

        if(article.getMedia().size() > 0)
            glideInstance.load(article.getMedia().get(0).getMediaMetadata().get(2).getUrl()).into(ivImage);
    }
}