package com.andrisasuke.app.cardnews.custom

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.andrisasuke.app.cardnews.R

class XTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    init {
        val styledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.XTextView)
        val fontFamily = styledAttributes.getInt(R.styleable.XTextView_fontFamily, XTextViewAttr.nsText)
        val fontStyle = styledAttributes.getInt(R.styleable.XTextView_fontStyle, XTextViewAttr.regular)
        styledAttributes.recycle()
        val fontName: String? = when(fontStyle) {
            XTextViewAttr.regular -> "fonts/SFNSTextCondensed-Regular.otf"
            XTextViewAttr.bold -> "fonts/SFNSTextCondensed-Bold.otf"
            else -> "fonts/SFNSTextCondensed-Regular.otf"
        }

        val typeface = Typeface.createFromAsset(getContext().assets, fontName)
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
                android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            setTypeface(typeface)
        }
    }

}

object XTextViewAttr {
    // font family
    val nsText = 1

    // font style
    val regular = 0
    val bold = 1
    val medium = 2
    val thin = 3
    val italic = 4
}