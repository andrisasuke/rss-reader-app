package com.andrisasuke.app.cardnews.model

data class NewsList(val status: String,
                    val source: String,
                    val sortBy: String,
                    val articles: List<News>)

data class News(val author: String,
                val title: String,
                val description: String,
                val url: String,
                val urlToImage: String,
                val publishedAt: String)

data class NewsHolder(val newsPopular: MutableList<News>, val newsLatest: MutableList<News>)

data class ErrorResponse(val status: String,
                         val code: String,
                         val message: String)