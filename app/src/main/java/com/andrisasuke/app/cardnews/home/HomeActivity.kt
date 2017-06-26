package com.andrisasuke.app.cardnews.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.andrisasuke.app.cardnews.BaseActivity
import com.andrisasuke.app.cardnews.R

import kotlinx.android.synthetic.main.activity_main.view.*

class HomeActivity : BaseActivity(), HomeView {

    override val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    override val appCompat: AppCompatActivity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNavigationClick() {
        finish()
    }
}