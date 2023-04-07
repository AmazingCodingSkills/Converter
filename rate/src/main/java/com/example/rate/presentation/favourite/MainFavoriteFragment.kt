package com.example.rate.presentation.favourite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.converter.core.view.VpAdapter
import com.example.rate.databinding.FragmentFavouritesBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.converter.core.R
import com.example.rate.presentation.favourite.allcurrency.CurrenciesFragment
import com.example.rate.presentation.favourite.onlyfavourite.OnlyFavoritesFragment


internal class MainFavoriteFragment() : Fragment() {

    private lateinit var flist : List<Fragment>
    private lateinit var tList : List<String>
    private lateinit var binding: FragmentFavouritesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        flist = listOf(OnlyFavoritesFragment.newInstance(), CurrenciesFragment.newInstance())
        tList = listOf(context.getString(R.string.only_favorite), context.getString(
            R.string.all_favorite))
    }

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
        binding.buttonBackMain.setOnClickListener {
            val fr = requireActivity().supportFragmentManager
            if (fr.backStackEntryCount > 0) {
                fr.popBackStack() // если в этой коробке что-то лежит, то на экран назад
            }
        }

    }

    private fun init() = with(binding) {
        val adapter = VpAdapter(activity as FragmentActivity, flist)
        viewPagerFavouritesScreen.adapter = adapter
        TabLayoutMediator(tabForFavourites, viewPagerFavouritesScreen) { tab, pos ->
            tab.text = tList[pos]
        }.attach()
    }
}






