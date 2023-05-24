package com.example.smartly.base

import androidx.fragment.app.Fragment
import com.example.smartly.utils.ErrorDialog
import com.example.smartly.utils.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var errorDialog: ErrorDialog

    protected fun showProgress() {
        try {
            if (this::progressDialog.isInitialized && progressDialog.isShowing) return
            progressDialog = ProgressDialog(requireContext())
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun hideProgress() {
        if (this::progressDialog.isInitialized && progressDialog.isShowing) progressDialog.dismiss()
    }

    protected fun showErrorDialog(message: String) {
        try {
            if (this::errorDialog.isInitialized && errorDialog.isShowing) return
            errorDialog = ErrorDialog(requireContext(), message)
            errorDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}