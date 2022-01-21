package com.example.articlesviewerapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticlesApiResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("num_results")
    private int num_results;
    @SerializedName("results")
    private ArrayList<Article> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    public ArrayList<Article> getResults() {
        return results;
    }

    public void setResults(ArrayList<Article> results) {
        this.results = results;
    }


}
