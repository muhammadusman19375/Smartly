package com.example.smartly.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.smartly.databinding.LayoutDialogErrorBinding

class ErrorDialog(context: Context, private val message: String) : Dialog(context) {
    private lateinit var binding: LayoutDialogErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = LayoutDialogErrorBinding.inflate(layoutInflater)
        this.setContentView(binding.root)
        this.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.setCancelable(false)
        binding.tvMessage.text = message

        binding.btnOk.setOnClickListener {
            this.dismiss()
        }
    }
}