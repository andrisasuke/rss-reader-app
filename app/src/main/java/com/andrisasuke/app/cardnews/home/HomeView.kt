package com.andrisasuke.app.cardnews.home

import com.andrisasuke.app.cardnews.model.NewsHolder
import com.andrisasuke.app.cardnews.network.NetworkError

interface HomeView {

    fun onSuccessFetchNews(source: String, newsHolder: NewsHolder)

    fun onFailedFetchNews(error: String)

    fun showLoading()

    fun dismissLoading()

    fun showError(error: String)
}