package com.andrisasuke.app.cardnews.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import com.andrisasuke.app.cardnews.BaseActivity
import com.andrisasuke.app.cardnews.R
import kotlinx.android.synthetic.main.about.*

class About : BaseActivity() {

    override val activity: AppCompatActivity by lazy { this }

    override val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(true)
        changeToolbarTitle(getString(R.string.about_lbl))
        renderView()
    }

    private fun renderView(){
        powered_txt.text = Html.fromHtml("Powered by <b>https://newsapi.org</b> API")
        github_text.setOnClickListener{
            launchUrl(getString(R.string.github_url))
        }
    }

    override fun onNavigationClick() {
        finish()
    }
}