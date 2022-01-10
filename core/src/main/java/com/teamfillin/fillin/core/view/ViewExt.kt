package com.teamfillin.fillin.core.view

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit
) {
    var previousClickedTime = 0L
    setOnClickListener { view ->
        val clickedTime = System.currentTimeMillis()
        if (clickedTime - previousClickedTime >= delay) {
            block(view)
            previousClickedTime = clickedTime
        }
    }
}

fun ImageView.load(
    @DrawableRes placeHolder: Int = -1,
    url: String
) {
    if (placeHolder == -1)
        Glide.with(context)
            .load(url)
            .into(this)
    else
        Glide.with(context)
            .load(url)
            .placeholder(placeHolder)
            .into(this)
}
