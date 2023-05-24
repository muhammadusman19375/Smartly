package com.example.smartly.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.smartly.R

class ProgressDialog(internal val context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        this.setContentView(R.layout.layout_progress_dialog)
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCancelable(false)
    }
}