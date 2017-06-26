package com.andrisasuke.app.cardnews.util

import android.content.Context
import android.util.TypedValue

fun String.toDateFormat() : String {
    return ""
}

fun Float.dpToPx(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
            resources.displayMetrics).toInt()
}