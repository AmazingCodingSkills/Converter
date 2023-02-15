package com.currency.converter.base

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.converter.R
import com.example.converter.databinding.DialogCustomBinding


class CustomDialog: DialogFragment() {

    private lateinit var binding: DialogCustomBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_corner)
        binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.repeatCheckInternetBtn.setOnClickListener {
            dismiss()
        }
        binding.goSettingsInternetBtn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
            dismiss()
        }
        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }
}



