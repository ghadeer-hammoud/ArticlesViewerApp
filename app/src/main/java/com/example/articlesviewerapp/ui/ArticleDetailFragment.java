package com.example.articlesviewerapp.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.articlesviewerapp.R;
import com.example.articlesviewerapp.Resource;
import com.example.articlesviewerapp.databinding.FragmentArticleDetailBinding;
import com.example.articlesviewerapp.di.ViewModelProviderFactory;
import com.example.articlesviewerapp.models.Article;
import com.example.articlesviewerapp.viewmodels.ArticleDetailsViewModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class ArticleDetailFragment extends DaggerFragment {

    public static final String ARG_ARTICLE_ID = "article_id";

    private CollapsingToolbarLayout mToolbarLayout;
    private ImageView ivImage;
    private TextView tvTitle, tvType, tvSection, tvByline, tvPublishedDate, tvUpdated, tvAbstract, tvSource, tvSeeOriginal;
    private FlexboxLayout layoutKeywords;
    private LinearLayout mainLayout;
    private TextView tvMessage;
    private ProgressBar progressBar;
    private FloatingActionButton fabSave;

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
        layoutKeywords = binding.layoutKeywords;
        mainLayout = binding.mainLayout;
        tvMessage = binding.tvMessage;
        progressBar = binding.progressBar;
        fabSave = binding.fabSave;


        viewModel = ViewModelProviders.of(getActivity(), providerFactory).get(ArticleDetailsViewModel.class);
        if (getArguments().containsKey(ARG_ARTICLE_ID)) {
            if(getArguments().getString(ARG_ARTICLE_ID) != null && !getArguments().getString(ARG_ARTICLE_ID).isEmpty())
                subscribeObservers();
            else{
                showProgressBar(false);
                showMainLayout(false);
                showTextMessage(true, "Click  item to show.");
            }
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showProgressBar(boolean isVisible){
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showTextMessage(boolean isVisible, String message){
        tvMessage.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        tvMessage.setText(isVisible ? message : "");
    }

    private void showMainLayout(boolean isVisible){
        mainLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        fabSave.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    private void subscribeObservers(){

        viewModel.observeArticle(Long.parseLong(getArguments().getString(ARG_ARTICLE_ID))).observe(getViewLifecycleOwner(), new Observer<Resource<Article>>() {
            @Override
            public void onChanged(Resource<Article> articleResource) {

                if(articleResource != null){
                    switch (articleResource.status){
                        case LOADING:{
                            showProgressBar(true);
                            showMainLayout(false);
                            showTextMessage(false, "");
                            break;
                        }
                        case SUCCESS:{
                            showProgressBar(false);
                            showMainLayout(true);
                            showTextMessage(false, "");
                            populateData(articleResource.data);

                            break;
                        }
                        case ERROR:{
                            showProgressBar(false);
                            showMainLayout(false);
                            showTextMessage(true, articleResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void populateData(Article article){

        mainLayout.setVisibility(View.VISIBLE);
        fabSave.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.GONE);

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

        String[] keywords = article.getAdxKeywords().split(";");
        for(String keyword : keywords){
            TextView tv = new TextView(getActivity(), null, 0, R.style.Pill_Green_Small);
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(1, 1, 1, 1);
            tv.setLayoutParams(lp);
            tv.setText(keyword);
            tv.setTextSize(Dimension.SP, 12);
            /*if (Build.VERSION.SDK_INT < 23) {
                tv.setTextAppearance(getActivity(), R.style.Pill_Green_Small);
            }
            else {
                tv.setTextAppearance(R.style.Pill_Green_Small);
            }*/
            layoutKeywords.addView(tv);
        }

        if(article.getMedia().size() > 0)
            glideInstance.load(article.getMedia().get(0).getMediaMetadata().get(2).getUrl()).into(ivImage);
    }
}