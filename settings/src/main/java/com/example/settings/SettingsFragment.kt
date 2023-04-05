package com.example.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.settings.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        shareApp()
        return binding.root

    }

    private fun shareApp() = with(binding) {
        shareButton.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = getString(R.string.type_plain)
            myIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link))
            myIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app))
            startActivity(Intent.createChooser(myIntent, getString(R.string.share_app)))
        }
    }
}



