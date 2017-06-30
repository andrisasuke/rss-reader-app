package com.andrisasuke.app.cardnews.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.andrisasuke.app.cardnews.BaseActivity
import com.andrisasuke.app.cardnews.BaseApp
import com.andrisasuke.app.cardnews.R
import com.andrisasuke.app.cardnews.about.About
import com.andrisasuke.app.cardnews.model.DataSource
import com.andrisasuke.app.cardnews.model.News
import com.andrisasuke.app.cardnews.model.NewsHolder
import com.andrisasuke.app.cardnews.network.ApiService
import com.andrisasuke.app.cardnews.util.LocalPreferences

import kotlinx.android.synthetic.main.home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView {

    companion object {
        private val TAG = "HomeActivity"
    }

    override val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

    val adapter: NewsAdapter by lazy {
        NewsAdapter(this.applicationContext, NewsHolder(mutableListOf(), mutableListOf())) {
            news ->
            news.gotoDetailNews()
        }
    }

    var menuItem: MenuItem? = null
    var presenter: HomePresenter? = null

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var localPreferences: LocalPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).deps.inject(this)
        setContentView(R.layout.home)
        presenter = HomePresenter(this, apiService, localPreferences, this)
        renderView()
    }

    private fun renderView() {
        progress.visibility = View.GONE
        initToolbar(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL;
        list_news.layoutManager = layoutManager
        list_news.adapter = adapter

        val cachedNews: NewsHolder? = localPreferences.getNewsCache()
        if(cachedNews != null) {
            val cachedSource = localPreferences.getNewsSource()
            changeToolbarTitle(cachedSource)
            onSuccessFetchNews(cachedSource, cachedNews)
        } else presenter?.getNews()

    }

    override fun onNavigationClick() {
        if(presenter != null) presenter?.onDestroy()
        finish()
    }

    override fun onSuccessFetchNews(source: String, newsHolder: NewsHolder) {
        if(!newsHolder.newsLatest.isEmpty()) {
            adapter.updateData(newsHolder)
            adapter.notifyDataSetChanged()
            changeToolbarTitle(source)
        }
    }

    override fun onFailedFetchNews(error: String) {
        showSnackbar(home_layout, error)
    }

    override fun showLoading() {
        if(adapter.newsHolder.newsLatest.isEmpty()) {
            progress.visibility = View.VISIBLE
        }
    }

    override fun dismissLoading() {
        progress.visibility = View.GONE
        menuItem?.collapseActionView();
        menuItem?.actionView = null;
    }

    override fun showError(error: String) {
        Log.e("Home", "error home $error")
        showSnackbar(home_layout, error)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        menuItem = menu?.getItem(0)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_home_refresh -> {
                menuItem = item
                menuItem?.setActionView(R.layout.progress_bar_small)
                menuItem?.expandActionView()
                val source = localPreferences.getNewsSource()
                presenter?.getNews(source)
            }
            R.id.menu_home_source -> {
                showSourceOption()
            }
            R.id.menu_home_about -> {
                startActivity(Intent(this, About::class.java))
            }
            else -> return false
        }
        return true
    }

    private fun News.gotoDetailNews(){
        Log.d(TAG, "news detail: ${this.title}")
        if (!TextUtils.isEmpty(this.url))
            launchUrl(this.url)
    }

    private fun showSourceOption() {
        val builder = AlertDialog.Builder(this)
        builder.setItems(DataSource.values) { dialog, which ->
            selectSource(DataSource.values[which])
            dialog.cancel()
        }
        builder.setTitle(R.string.select_source_title)
        builder.show()
    }

    private fun selectSource(source: String){
        presenter?.getNews(source)
        menuItem?.setActionView(R.layout.progress_bar_small)
        menuItem?.expandActionView()
    }
}