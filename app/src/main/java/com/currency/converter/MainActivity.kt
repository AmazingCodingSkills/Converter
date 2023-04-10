package com.currency.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.converter.core.databinding.ActivityMainBinding
import com.example.rate.presentation.latestrates.LatestRatesFragment
import com.example.calculator.presentation.CalculatorFragment
import com.example.settings.SettingsFragment
import com.converter.core.R


class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTab: Int? = null
    private var firstTabVisited: Boolean = false
    private var secondTabVisited: Boolean = false
    private var thirdTabVisited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            setInitialScreen()
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    switchTab(LatestRatesFragment.newInstance(), RATES, R.id.ic_home)
                }
                R.id.ic_convert -> {
                    switchTab(CalculatorFragment.newInstance(), CALCULATOR, R.id.ic_convert)
                }
                R.id.ic_settings -> {
                    switchTab(SettingsFragment(), SETTINGS, R.id.ic_settings)
                }
            }
            true
        }
        currentTab?.let { binding.bottomNavigation.selectedItemId }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedItemId", requireNotNull(currentTab))
        outState.putBoolean("firstTabVisited", firstTabVisited)
        outState.putBoolean("secondTabVisited", secondTabVisited)
        outState.putBoolean("thirdTabVisited", thirdTabVisited)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val selectedItemId = savedInstanceState.getInt("selectedItemId")
        firstTabVisited = savedInstanceState.getBoolean("firstTabVisited", firstTabVisited)
        secondTabVisited = savedInstanceState.getBoolean("secondTabVisited", secondTabVisited)
        thirdTabVisited = savedInstanceState.getBoolean("thirdTabVisited", thirdTabVisited)
        currentTab = selectedItemId
    }

    private fun setInitialScreen() {
        val fragment = LatestRatesFragment.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.bottom_navigation_container,
                fragment,
                fragment::javaClass.name
            )
            addToBackStack(RATES)
        }
        currentTab = R.id.ic_home
        firstTabVisited = true
    }

    private fun switchTab(fragment: Fragment, tab: String, menuId: Int) {
        with(supportFragmentManager) {
            when (currentTab) {
                R.id.ic_home -> {
                    firstTabVisited = true
                    supportFragmentManager.saveBackStack(RATES)
                }
                R.id.ic_convert -> {
                    secondTabVisited = true
                    supportFragmentManager.saveBackStack(CALCULATOR)
                }
                R.id.ic_settings -> {
                    thirdTabVisited = true
                    supportFragmentManager.saveBackStack(SETTINGS)
                }
            }
            currentTab = menuId

            if (firstTabVisited.not() || secondTabVisited.not() || thirdTabVisited.not()) {
                commit {
                    setReorderingAllowed(true)
                    replace(R.id.bottom_navigation_container, fragment, fragment::class.simpleName)
                    addToBackStack(tab)
                }
            } else {
                restoreBackStack(tab)
            }
        }
    }

    private companion object {
        const val RATES = "rates"
        const val CALCULATOR = "calculator"
        const val SETTINGS = "settings"
    }
}

