package com.andrisasuke.app.cardnews

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

interface ToolbarManager {

    val toolbar: Toolbar
    val appCompat: AppCompatActivity

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar() {
        appCompat.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onNavigationClick()
        }
        appCompat.supportActionBar?.setHomeButtonEnabled(false)
        appCompat.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        appCompat.supportActionBar?.setDisplayShowHomeEnabled(false)

    }

    fun onNavigationClick()

}