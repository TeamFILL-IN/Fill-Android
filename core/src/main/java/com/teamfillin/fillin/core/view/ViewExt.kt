package com.teamfillin.fillin.core.view

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
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

class ItemDiffCallback<T>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(
        oldItem: T, newItem: T
    ): Boolean = onItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: T, newItem: T
    ): Boolean = onContentsTheSame(oldItem, newItem)
}
