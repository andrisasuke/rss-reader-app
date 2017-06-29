package com.andrisasuke.app.cardnews

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

interface ToolbarManager {

    val toolbar: Toolbar
    val activity: AppCompatActivity

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar(useNavigation: Boolean = false) {
        activity.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onNavigationClick()
        }
        activity.supportActionBar?.setHomeButtonEnabled(useNavigation)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(useNavigation)
        activity.supportActionBar?.setDisplayShowHomeEnabled(useNavigation)

    }

    fun onNavigationClick()

}