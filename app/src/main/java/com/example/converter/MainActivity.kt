package com.example.converter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.converter.databinding.ActivityMainBinding
import com.example.converter.fragment.CurrencyFragment
import com.example.converter.fragment.LatestValueFragment
import com.example.converter.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int = R.id.ic_home
    private val someClass = LatestValueFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActionBar()?.setTitle("Main")

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

        val latestValueFragment = LatestValueFragment.newInstance()
        val historicalFragment = CurrencyFragment.newInstance()
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
        transaction.commit()
    }
}




