package com.andrisasuke.app.cardnews.network

import android.content.Context
import android.util.Log
import com.andrisasuke.app.cardnews.BuildConfig

import com.andrisasuke.app.cardnews.model.ApiResponseCallback
import com.andrisasuke.app.cardnews.model.NewsHolder
import com.andrisasuke.app.cardnews.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ApiService (val context: Context, val apiNetworkService: ApiNetworkService) {

    companion object {
        val SORT_LATEST = "latest"
        val SORT_POPULAR = "top"
        val TAG = "ApiService"
    }

    interface ApiNetworkService {

        @GET("/v1/articles")
        fun list(@Query("source") source: String, @Query("sortBy") sortBy: String,
                   @Query("apiKey") apiKey: String): Observable<NewsList>

    }

    fun getNews( source: String, callback: ApiResponseCallback<NewsHolder>): Subscription {
        val apiKey = BuildConfig.API_KEY
        val newsPopularObservable = apiNetworkService.list(source, SORT_POPULAR, apiKey);
        val newLatestObservable = apiNetworkService.list(source, SORT_LATEST, apiKey);
        val zipped: Observable<NewsHolder> = Observable.zip(newsPopularObservable,
                newLatestObservable ) { t1, t2 ->
                    NewsHolder(t1.articles.toMutableList(), t2.articles.toMutableList())
        }
        return zipped.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { newHolder -> callback.onSuccess(newHolder)},
                        { error ->
                            Log.e(TAG, "Error fetching news, ${error.message}")
                            callback.onFailed(NetworkError(context, error = error))
                        }
                )
    }
}