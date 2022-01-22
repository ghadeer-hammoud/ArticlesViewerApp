package com.example.articlesviewerapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.articlesviewerapp.R;
import com.example.articlesviewerapp.Resource;
import com.example.articlesviewerapp.adapters.ArticlesRecyclerAdapter;
import com.example.articlesviewerapp.databinding.FragmentArticlesListBinding;
import com.example.articlesviewerapp.di.ViewModelProviderFactory;
import com.example.articlesviewerapp.models.Article;
import com.example.articlesviewerapp.viewmodels.ArticlesViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;


public class ArticlesListFragment extends DaggerFragment implements ArticlesRecyclerAdapter.OnListItemClickedListener {

    private FragmentArticlesListBinding binding;
    private View itemDetailFragmentContainer;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArticlesRecyclerAdapter mAdapter;
    private ArticlesViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    RequestManager glideInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(getActivity(), providerFactory).get(ArticlesViewModel.class);
        binding = FragmentArticlesListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        recyclerView = binding.recyclerView;
        progressBar = binding.progressBar;
        itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        init();
    }

    private void init() {

        setupRecyclerView();

        subscribeObservers();

    }

    private void setupRecyclerView() {

        mAdapter = new ArticlesRecyclerAdapter(glideInstance, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void subscribeObservers(){

        viewModel.observeArticles().observe(getViewLifecycleOwner(), new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {

                if(listResource != null){
                    switch (listResource.status){
                        case LOADING:{
                            showLoading(true);
                            break;
                        }
                        case SUCCESS:{
                            showLoading(false);
                            mAdapter.setArticles(listResource.data);
                            break;
                        }
                        case ERROR:{
                            showLoading(false);
                            Toast.makeText(getActivity(), listResource.message, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onListItemClicked(View itemView) {

        Article article = (Article) itemView.getTag();

        Bundle arguments = new Bundle();
        arguments.putString(ArticleDetailFragment.ARG_ARTICLE_ID, String.valueOf(article.getId()));
        if (itemDetailFragmentContainer != null) {
            Navigation.findNavController(itemDetailFragmentContainer)
                    .navigate(R.id.fragment_article_detail, arguments);
        } else {
            Navigation.findNavController(itemView).navigate(R.id.show_article_detail, arguments);
        }
    }

    private void showLoading(boolean isLoading){
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}