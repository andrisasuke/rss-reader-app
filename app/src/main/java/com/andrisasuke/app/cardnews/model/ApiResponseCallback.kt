package com.andrisasuke.app.cardnews.model

import com.andrisasuke.app.cardnews.network.NetworkError

interface ApiResponseCallback<in T> {

    fun onSuccess(result: T)

    fun onFailed(error: NetworkError)
}
