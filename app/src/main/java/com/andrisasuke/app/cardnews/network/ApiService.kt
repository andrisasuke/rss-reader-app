package com.andrisasuke.app.cardnews.network

import android.content.Context
import com.andrisasuke.app.cardnews.model.ApiResponseCallback
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
        val SORT_POPULAR = "popular"
    }

    interface ApiNetworkService {

        @GET("/v1/articles")
        fun list(@Query("source") source: String, @Query("sortBy") sortBy: String,
                   @Query("apiKey") apiKey: String): Observable<NewsList>

    }

    fun getNews(sortBy: String, source: String, callback: ApiResponseCallback<NewsList>): Subscription {
        val apiKey = "41651ce701f9439188061d47d5477a90"
        return apiNetworkService.list(source, sortBy, apiKey).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { newList -> callback.onSuccess(newList)},
                        { error -> callback.onFailed(NetworkError(context, error = error))}
                );
    }
}