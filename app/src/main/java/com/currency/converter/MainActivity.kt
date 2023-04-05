package com.currency.converter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.converter.core.databinding.ActivityMainBinding
import com.example.rate.presentation.latestrates.LatestRatesFragment
import com.example.calculator.presentation.CalculatorFragment
import com.example.settings.SettingsFragment
import com.converter.core.R
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int = R.id.ic_home

    private val latestValueFragment = LatestRatesFragment.newInstance()
    private val calculatorFragment = CalculatorFragment.newInstance()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.title = "Main"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            makeCurrentFragment(latestValueFragment)
        }

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

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        var handled = false
        for (fragment in fragmentList) {
            if (fragment.isVisible) {
                if (fragment is LatestRatesFragment) {
                    handled = false
                } else {
                    makeCurrentFragment(latestValueFragment)
                    currentTab = R.id.ic_home
                    binding.bottomNavigation.selectedItemId = currentTab
                    handled = true
                }
                break
            }
        }
        if (!handled) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                super.onBackPressed()
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", binding.bottomNavigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItemId = savedInstanceState.getInt("selectedItemId")
        binding.bottomNavigation.selectedItemId = selectedItemId
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.bottom_navigation_container, fragment)
        //transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }
}

/*
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int = R.id.ic_home

    private val latestValueFragment = LatestRatesFragment.newInstance()
    private val calculatorFragment = CalculatorFragment.newInstance()
    private val settingsFragment = SettingsFragment()

    private val screens = listOf(latestValueFragment, calculatorFragment, settingsFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.title = "Main"

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            screens.forEach {
                supportFragmentManager.beginTransaction()
                    .add(R.id.bottom_navigation_container, it)
                    .hide(it)
                    .commit()
            }
            makeCurrentFragment(latestValueFragment)
        }

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

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        var handled = false
        for (fragment in fragmentList) {
            if (fragment.isVisible) {
                if (fragment is LatestRatesFragment) {
                    handled = false
                } else {
                    makeCurrentFragment(latestValueFragment)
                    currentTab = R.id.ic_home
                    binding.bottomNavigation.selectedItemId = currentTab
                    handled = true
                }
                break
            }
        }

        this.onBackPressedDispatcher.addCallback {
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                super.onBackPressed()
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", binding.bottomNavigation.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItemId = savedInstanceState.getInt("selectedItemId")
        binding.bottomNavigation.selectedItemId = selectedItemId
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.bottom_navigation_container, fragment)
        transaction.show(fragment)
        //transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }
}*/
