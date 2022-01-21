package com.example.articlesviewerapp.di;

import android.app.Application;
import android.content.SharedPreferences;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.articlesviewerapp.utils.Constants;
import javax.inject.Singleton;

import androidx.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static RequestOptions provideGlideRequestOptions(){
        return RequestOptions
                .placeholderOf(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreferenceInstance(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
