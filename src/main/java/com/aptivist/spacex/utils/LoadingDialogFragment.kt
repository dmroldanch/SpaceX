package com.aptivist.spacex.utils

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.aptivist.spacex.R
import com.aptivist.spacex.databinding.FragmentLoadingDialogBinding
import javax.annotation.Nullable


class LoadingDialogFragment : DialogFragment() {

    private var _bindingDialogFragment: FragmentLoadingDialogBinding? = null
    private val bindingDialogFragment get() = _bindingDialogFragment

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        _bindingDialogFragment = FragmentLoadingDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), R.style.DialogTheme)
        dialog.window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(requireContext(),R.color.transparent_black)))
        dialog.setCancelable(false)
        bindingDialogFragment?.let {
            dialog.setContentView(it.root)
        }

        return dialog
    }

    fun showDialog(fragmentManager: FragmentManager){
        this.isCancelable = false
        if (!this.isAdded){
            show(fragmentManager, TAG)
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        }catch (e: Exception){}

    }
    companion object {
        val TAG: String = LoadingDialogFragment::class.java.simpleName
        fun newInstance() = LoadingDialogFragment()
    }
}