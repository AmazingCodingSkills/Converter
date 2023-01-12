package com.currency.converter.features.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.currency.converter.base.VpAdapter
import com.example.converter.databinding.FragmentFavouritesBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainFavoriteFragment() : Fragment() {

    private val flist = listOf(OnlyFavoritesFragment.newInstance(),CurrenciesFragment.newInstance())
    private val tList = listOf("Избранное", "Все")
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.arrowBackMain.setOnClickListener {
            val fr = requireActivity().supportFragmentManager
            if (fr.backStackEntryCount > 0) {
                fr.popBackStack() // если в этой коробке что-то лежит, то на экран назад
            }
        }
    }

    private fun init() = with(binding) {
        val adapter = VpAdapter(activity as FragmentActivity, flist)
        viewPager.adapter = adapter
        TabLayoutMediator(tablayout, viewPager) { tab, pos ->
            tab.text = tList[pos]
        }.attach()
    }
}






