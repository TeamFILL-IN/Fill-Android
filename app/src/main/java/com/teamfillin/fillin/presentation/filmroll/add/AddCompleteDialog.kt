package com.teamfillin.fillin.presentation.filmroll.add

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.ImageButton
import com.teamfillin.fillin.R

class AddCompleteDialog(context: Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_addphoto_complete)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val btnExit = dialog.findViewById<ImageButton>(R.id.btn_exit)

        btnExit.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun interface OnDialogClickListener {
        fun onClicked()
    }
}