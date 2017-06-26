package com.andrisasuke.app.cardnews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

class MainActivity : BaseActivity() {

    override val toolbar: Toolbar by lazy { findViewById(R.id.action_bar) as Toolbar }

    override val appCompat: AppCompatActivity by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
    }

    override fun onNavigationClick() {
        finish()
    }
}
