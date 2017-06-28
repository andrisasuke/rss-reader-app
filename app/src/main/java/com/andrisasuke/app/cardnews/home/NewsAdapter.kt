package com.andrisasuke.app.cardnews.home

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.andrisasuke.app.cardnews.R
import com.andrisasuke.app.cardnews.model.News
import com.andrisasuke.app.cardnews.model.NewsHolder
import com.andrisasuke.app.cardnews.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.news_item_no_photo.view.*
import kotlinx.android.synthetic.main.news_header_item.view.*

class NewsAdapter(val context: Context, val newsHolder: NewsHolder, val itemClick: (News) -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val HEADER_TYPE = 1;
        val ROW_TYPE = 2;
        val TAG = "NewsAdapter"
    }

    private var mBackground: Int = 0

    init {
        val mTypedValue = TypedValue()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.theme.resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true)
            mBackground = mTypedValue.resourceId
            if (mBackground == 0) mBackground = R.drawable.abc_item_background_holo_light;
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val news = newsHolder.newsLatest[position]
        if (holder is HeaderViewHolder){
            holder.bindNewsHeader(news)
        } else {
            (holder as ItemViewHolder).bindNews(news)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER_TYPE) {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.news_header_item,
                    parent, false)
            return HeaderViewHolder(itemView, newsHolder.newsPopular)
        } else {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.news_item_no_photo,
                    parent, false)
            return ItemViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int = newsHolder.newsLatest.size

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 ->  HEADER_TYPE
            else -> ROW_TYPE
        }
    }

    fun updateData(data: NewsHolder) {
        with(data) {
            newsHolder.newsPopular.clear()
            val newPopularSublist = if(newsPopular.size <= 7) {
                newsPopular.subList(0, newsPopular.size)
            } else{
                newsPopular.subList(0, 6)
            }
            newsHolder.newsPopular.addAll(newPopularSublist)

            newsHolder.newsLatest.clear()
            newsHolder.newsLatest.addAll(newsLatest)
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindNews(news: News) {
            with(news) {
                itemClickBackground(itemView, mBackground)
                itemView.news_title.text = Html.fromHtml(title)
                itemView.news_description.text = if(!TextUtils.isEmpty(description)) Html.fromHtml(description) else context.getString(R.string.no_description)
                itemView.news_author.text = author
                itemView.news_time.text = if(!TextUtils.isEmpty(publishedAt)) publishedAt.toDateFormat() else context.getString(R.string.today_lbl)
                itemView.news_row.setOnClickListener { itemClick(this) }
            }
        }
    }

    inner class HeaderViewHolder( view: View,
                           val newsPopular: List<News>) : RecyclerView.ViewHolder(view) {

        fun bindNewsHeader(news: News) {

            with(news) {
                itemClickBackground(itemView.news_item, mBackground)
                itemView.news_title.text = Html.fromHtml(title)
                itemView.news_description.text = if(!TextUtils.isEmpty(description)) Html.fromHtml(description) else context.getString(R.string.no_description)
                itemView.news_author.text = author
                itemView.news_time.text = if(!TextUtils.isEmpty(publishedAt)) publishedAt.toDateFormat() else context.getString(R.string.today_lbl)

                itemView.news_item.setOnClickListener { itemClick(this) }
                renderNewsPhoto(itemView.header_news_photo_container)
            }

        }

        private fun renderNewsPhoto(view: LinearLayout){
            view.removeAllViews()
            for (ns: News in newsPopular) {
                if (TextUtils.isEmpty(ns.urlToImage)) continue
                val mView = LayoutInflater.from(context).
                        inflate(R.layout.photo_item_caption, null, false)
                val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                val layoutMargin = 5f.dpToPx(context)
                params.setMargins(layoutMargin, layoutMargin, layoutMargin, layoutMargin)
                val layoutPadding = 5f.dpToPx(context)
                mView.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding)
                val photoTitle = mView.findViewById(R.id.news_photo_title) as TextView
                val photoImg = mView.findViewById(R.id.news_photo_image) as ImageView

                mView.layoutParams = params
                photoTitle.text = ns.title
                photoImg.setOnClickListener { itemClick(ns) }

                loadImage(photoImg, ns.urlToImage, R.drawable.frame)

                view.addView(mView)
            }
        }

        private fun loadImage(target: ImageView, url: String, @DrawableRes defaultImg: Int) {
            Glide.with(context).load(url).centerCrop()
                    .error(defaultImg).placeholder(defaultImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(target)
        }
    }

}