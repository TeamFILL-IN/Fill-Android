package com.teamfillin.fillin.core.design

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.teamfillin.fillin.core.context.colorOf

fun Activity.statusBarColorOf(@ColorRes resId: Int) {
    window?.statusBarColor = colorOf(resId)
}

fun Fragment.statusBarColorOf(@ColorRes resId: Int) {
    requireActivity().statusBarColorOf(resId)
}
