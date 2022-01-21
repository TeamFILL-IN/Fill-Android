package com.teamfillin.fillin.presentation.filmroll.add

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.teamfillin.fillin.R

class AddCancelDialog(context: Context) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) {
        onClickListener = listener
    }

    fun showDialog() {
        dialog.setContentView(R.layout.dialog_addphoto_cancel)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val btnCancel = dialog.findViewById<AppCompatButton>(R.id.btn_cancel)
        val btnContinue = dialog.findViewById<AppCompatButton>(R.id.btn_continue)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            onClickListener.onClicked()
        }

        btnContinue.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun interface OnDialogClickListener {
        fun onClicked()
    }
}