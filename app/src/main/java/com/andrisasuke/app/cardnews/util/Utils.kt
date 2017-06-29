package com.andrisasuke.app.cardnews.util

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.text.format.DateUtils
import android.util.TypedValue
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toDateFormat(format: String = "yyyy-MM-dd'T'hh:mm:ss'Z'") : String {
    val sd = SimpleDateFormat(format, Locale.getDefault())
    val timezone = TimeZone.getDefault()
    try {
        val dt = sd.parse(this)
        val offset = timezone.getOffset(dt.time) / 1000 / 60 / 60
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR, offset)
        cal.time = dt
        val result = DateUtils.getRelativeTimeSpanString(cal.time.time)
        return result.toString()
    } catch (ex: ParseException) {
        return "today"
    }
}

fun Float.dpToPx(context: Context): Int {
    val resources = context.resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
            resources.displayMetrics).toInt()
}

fun itemClickBackground(view: View, @DrawableRes res: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
        view.setBackgroundResource(res)
    }
}