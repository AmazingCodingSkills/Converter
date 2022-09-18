package com.example.converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.converter.databinding.ActivityMainBinding
import com.example.converter.fragment.CurrencyFragment
import com.example.converter.fragment.LatestValueFragment
import com.example.converter.fragment.SettingsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val latestValueFragment = LatestValueFragment.newInstance()
        val historicalFragment = CurrencyFragment.newInstance()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(latestValueFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(latestValueFragment)
                R.id.ic_convert -> makeCurrentFragment(historicalFragment)
                R.id.ic_settings -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}