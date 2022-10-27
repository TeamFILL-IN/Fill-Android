package com.teamfillin.fillin.core.view

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:isVisible")
    fun View.setVisibleIf(value: Boolean) {
        isVisible = value
    }
}