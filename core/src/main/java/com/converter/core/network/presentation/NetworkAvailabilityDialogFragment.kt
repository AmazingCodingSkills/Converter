package com.converter.core.network.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.converter.core.R
import com.converter.core.databinding.DialogCustomBinding


class NetworkAvailabilityDialogFragment() : DialogFragment() {

    private lateinit var binding: DialogCustomBinding
    var onReload: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_corner)
        binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogWork()
    }


    private fun dialogWork() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.repeatCheckInternetBtn.setOnClickListener {
            onReload?.invoke()
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




