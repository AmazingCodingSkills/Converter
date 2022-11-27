package com.example.converter.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.converter.databinding.FragmentSettings2Binding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettings2Binding? = null
    private val binding get() = _binding!!

    private val preferences: SharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        requireActivity().applicationContext.getSharedPreferences(SettingsPreferences, MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettings2Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserData()

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            saveUserData(
                name = name,
                email = email
            )
            Log.d("opachcki","Settings")
        }

        binding.clearButton.setOnClickListener {
            clear()
        }
    }

    private fun loadUserData() {
        val name = preferences.getString(Name, "")
        val email = preferences.getString(Email, "")

        binding.apply {
            nameEditText.setText(name)
            emailEditText.setText(email)
        }
    }

    private fun saveUserData(name: String, email: String) {
        preferences.edit()
            .putString(Name, name)
            .putString(Email, email)
            .apply()
    }

    private fun clear() {
        preferences.edit()
            .clear()
            .apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // посмотреть на утечки памяти, так правильно, если этого нет будут утечки
    }

    private companion object {
        const val SettingsPreferences = "SettingsPreferences"
        const val Name = "NameKey"
        const val Email = "EmailKey"
    }
}

/*data class UserCurrency(
    val itemModel: ItemModel,
    val favorite: Boolean
)

val list = listOf()*/
