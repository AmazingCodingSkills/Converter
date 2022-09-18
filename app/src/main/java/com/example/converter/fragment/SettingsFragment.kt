package com.example.converter.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.example.converter.R
import com.example.converter.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {


    var editTextName: EditText? = null
    var editTextEmail: EditText? = null
    lateinit var textViewName: TextView
    lateinit var textViewEmail: TextView
    private val myPreference = "myPref"
    private val name = "nameKey"
    private val email = "emailKey"
    var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings,container,false)

    }


    }
