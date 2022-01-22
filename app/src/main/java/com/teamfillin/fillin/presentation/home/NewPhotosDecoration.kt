package com.teamfillin.fillin.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.design.dp

class NewPhotosDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = 18.dp
            outRect.right = 4.dp
        } else {
            with(outRect) {
                left = 4.dp
                right = 4.dp
            }
        }

    }
}