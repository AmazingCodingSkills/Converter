package com.currency.converter.features.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.converter.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(){
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        shareApp()
        return binding.root

    }
    fun shareApp() = with(binding){
        shareButton.setOnClickListener {
           val myIntent = Intent(Intent.ACTION_SEND)
           myIntent.type = "type/plain"
           val shareBody = "market://..."
           val shareSub = "Поделиться приложением"
           myIntent.putExtra(Intent.EXTRA_TEXT,shareBody)
           myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub)
           startActivity(Intent.createChooser(myIntent,"Share your app"))
       }
    }
}



