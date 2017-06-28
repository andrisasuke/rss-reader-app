package com.andrisasuke.app.cardnews

import android.app.Application
import com.andrisasuke.app.cardnews.network.ApiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.server_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideHttpClient())
                .build();
        val apiNetworkService = retrofit.create(ApiService.ApiNetworkService::class.java)
        return ApiService(this, apiNetworkService)
    }

    private fun provideHttpClient(): OkHttpClient {
        val cacheDir = File(cacheDir, "card-news-http-cache");
        val cache = Cache(cacheDir, 1024 * 1024 * 10);
        val logLevel = when(BuildConfig.DEBUG){
            true   -> HttpLoggingInterceptor.Level.BODY
            else     -> HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
                .cache(cache)
                .build();
    }
}