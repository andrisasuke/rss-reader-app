package com.andrisasuke.app.cardnews

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity(), ToolbarManager {

    protected var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showSnackbar(view: View, message: String) {
        if (this.snackbar != null && (this.snackbar?.isShown() as Boolean) ){
            this.snackbar?.dismiss()
        }
        this.snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.ok_lbl) {
                   snackbar?.dismiss()
                }
        this.snackbar?.show()
    }

}