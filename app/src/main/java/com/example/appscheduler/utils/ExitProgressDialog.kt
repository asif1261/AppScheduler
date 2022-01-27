package com.example.appscheduler.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.appscheduler.R

class ExitProgressDialog {
    companion object{
        fun progressDialog(context: Context) : Dialog{
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.exit_progress_dialog, null, false)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))

            return dialog
        }
    }
}