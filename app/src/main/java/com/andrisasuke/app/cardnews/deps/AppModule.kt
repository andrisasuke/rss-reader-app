package com.andrisasuke.app.cardnews.deps

import android.content.Context
import com.andrisasuke.app.cardnews.BuildConfig
import com.andrisasuke.app.cardnews.R
import com.andrisasuke.app.cardnews.network.ApiService
import com.andrisasuke.app.cardnews.util.LocalPreferences
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideLocalPreferences(context: Context): LocalPreferences{
        return LocalPreferences(context)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel = when(BuildConfig.DEBUG){
            true   -> HttpLoggingInterceptor.Level.BODY
            else     -> HttpLoggingInterceptor.Level.NONE
        }
        return HttpLoggingInterceptor().setLevel(logLevel)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context,
                                  loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        val cacheDir = File(context.cacheDir, "card-news-http-cache")
        val cache = Cache(cacheDir, (1024 * 1024 * 10).toLong())

        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(context: Context, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.server_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesApiNetworkService(retrofit: Retrofit): ApiService.ApiNetworkService {
        return retrofit.create<ApiService.ApiNetworkService>(ApiService.ApiNetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(context: Context, apiNetworkService: ApiService.ApiNetworkService): ApiService {
        return ApiService(context, apiNetworkService)
    }
}