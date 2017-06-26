package com.andrisasuke.app.cardnews.network

import android.content.Context
import com.andrisasuke.app.cardnews.R
import com.andrisasuke.app.cardnews.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException

class NetworkError(val context: Context, val error: Throwable ): Throwable() {

    val gson by lazy { Gson() }

    fun getAppErrorMessage(): String {
        when(this.error){
            is IOException -> return context.getString(R.string.common_error_internet)
            !is HttpException -> return context.getString(R.string.common_error_processing)
            else -> {
                val response = (this.error).response()
                if (response != null) {
                    return getJsonStringFromResponse()
                }
                return context.getString(R.string.common_error_processing)
            }
        }
    }

    private fun getJsonStringFromResponse(): String {
        val body = (error as HttpException).response().errorBody()
        try {
            val bodyText = body?.string();
            val errorResponse = gson.fromJson(bodyText, ErrorResponse::class.java)
            if(errorResponse != null && !errorResponse.message.isEmpty())
                return errorResponse.message
            else return context.getString(R.string.internal_server_error);
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return context.getString(R.string.common_error_processing);
    }
}