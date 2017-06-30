package com.andrisasuke.app.cardnews

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {

    protected var snackbar: Snackbar? = null
    //protected val localPreferences: LocalPreferences by lazy { LocalPreferences(this) }
    //protected val apiService: ApiService by lazy { (application as BaseApp).provideApiService() }
    protected val handler by lazy {  Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showSnackbar(view: View, message: String) {
        if (this.snackbar != null && (this.snackbar?.isShown as Boolean) ){
            this.snackbar?.dismiss()
        }
        this.snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.ok_lbl) {
                   snackbar?.dismiss()
                }
        this.snackbar?.show()
    }

    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        return handler.postDelayed(r, delayMillis)
    }

    fun changeToolbarTitle(title: String){
        postDelayed(Runnable {
            toolbar.title = title
        }, 50)
    }

    fun launchUrl(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true).setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_arrow_back_white))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }
}