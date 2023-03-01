package com.currency.converter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.currency.converter.features.calculator.presentation.CalculatorFragment
import com.currency.converter.features.rate.presentation.LatestRatesFragment
import com.currency.converter.features.settings.SettingsFragment
import com.example.converter.R
import com.example.converter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int = R.id.ic_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.title = "Main"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val latestValueFragment = LatestRatesFragment.newInstance()
        val calculatorFragment = CalculatorFragment.newInstance()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(latestValueFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    makeCurrentFragment(latestValueFragment)
                    currentTab = R.id.ic_home
                }
                R.id.ic_convert -> {
                    makeCurrentFragment(calculatorFragment)
                    currentTab = R.id.ic_convert
                }
                R.id.ic_settings -> {
                    makeCurrentFragment(settingsFragment)
                    currentTab = R.id.ic_settings
                }
            }
            true
        }
        binding.bottomNavigation.selectedItemId = currentTab

    }

        private fun makeCurrentFragment(fragment: Fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.bottom_navigation_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
}




