package com.andrisasuke.app.cardnews.home

import android.content.Context
import android.util.Log

import com.andrisasuke.app.cardnews.model.ApiResponseCallback
import com.andrisasuke.app.cardnews.model.DataSource
import com.andrisasuke.app.cardnews.model.NewsHolder
import com.andrisasuke.app.cardnews.network.ApiService
import com.andrisasuke.app.cardnews.network.NetworkError
import com.andrisasuke.app.cardnews.util.LocalPreferences
import rx.subscriptions.CompositeSubscription

class HomePresenter(val context: Context, val apiService: ApiService,
                    val localPreferences: LocalPreferences, val homeView: HomeView) {

    val subscriptions = CompositeSubscription()

    companion object {
        val TAG = "HomePresenter"
    }

    fun getNews(source: String = DataSource.values[0]){
        homeView.showLoading()
        val subscription = apiService.getNews(source, object : ApiResponseCallback<NewsHolder>{
            override fun onSuccess(result: NewsHolder) {
                Log.d(TAG, "Getting response news: ${result.newsLatest}")
                homeView.onSuccessFetchNews(source, result)
                homeView.dismissLoading()
                if(!result.newsLatest.isEmpty() && !result.newsPopular.isEmpty()) {
                    localPreferences.storeNewsSource(source)
                    localPreferences.storeNewsCache(result)
                }
            }

            override fun onFailed(error: NetworkError) {
                val msg = error.getAppErrorMessage()
                Log.e(TAG, "Error fetching news, ${error.message}")
                homeView.onFailedFetchNews(msg)
                homeView.dismissLoading()
            }

        })

        subscriptions.add(subscription)
    }

    fun onDestroy(){
        subscriptions.unsubscribe()
    }

}