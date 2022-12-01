package com.currency.converter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.currency.converter.features.calculator.CalculatorFragment
import com.currency.converter.features.rate.LatestRatesFragment
import com.currency.converter.features.settings.SettingsFragment
import com.example.converter.R
import com.example.converter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int = R.id.ic_home
    private val someClass = LatestRatesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActionBar()?.setTitle("Main")

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

        val latestValueFragment = LatestRatesFragment.newInstance()
        val historicalFragment = CalculatorFragment.newInstance()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(latestValueFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    makeCurrentFragment(latestValueFragment)
                    currentTab = R.id.ic_home
                }
                R.id.ic_convert -> {
                    makeCurrentFragment(historicalFragment)
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
        transaction.replace(R.id.fl_wrapper, fragment)
       // transaction.addToBackStack(null) пока явно не добавлю, с любого фрагмента с боттом навигейшн будет останавливаться приложение
        transaction.commit()
    }
}




