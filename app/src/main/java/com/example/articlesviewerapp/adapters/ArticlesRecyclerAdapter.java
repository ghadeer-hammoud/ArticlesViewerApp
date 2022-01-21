package com.example.articlesviewerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.articlesviewerapp.R;
import com.example.articlesviewerapp.databinding.ActivityArticlesBinding;
import com.example.articlesviewerapp.databinding.ArticleListItemBinding;
import com.example.articlesviewerapp.models.Article;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticlesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    RequestManager glideInstance;

    private List<Article> articles;
    private OnListItemClickedListener onListItemClickedListener;


    public ArticlesRecyclerAdapter(RequestManager glideInstance, OnListItemClickedListener onListItemClickedListener) {
        this.glideInstance = glideInstance;
        this.onListItemClickedListener = onListItemClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ArticleViewHolder) holder).bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivThumbnail;
        TextView tvTitle, tvByline, tvPublishedDate;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvByline = itemView.findViewById(R.id.tvByline);
            tvPublishedDate = itemView.findViewById(R.id.tvPublishedDate);

            itemView.setOnClickListener(this);
        }

        public void bind(Article article){

            tvTitle.setText(article.getTitle());
            tvByline.setText(article.getByline());
            tvPublishedDate.setText(article.getPublishedDate());

            if(article.getMedia().size() > 0)
                glideInstance
                        .load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                        .into(ivThumbnail);

        }

        @Override
        public void onClick(View view) {

            view.setTag(articles.get(getLayoutPosition()));
            onListItemClickedListener.onListItemClicked(view);
        }
    }

    public interface OnListItemClickedListener{
        void onListItemClicked(View itemView);
    }
}