package com.example.converter.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.MyCustomApplicationClass
import com.example.converter.adaptersCurrencies.ConvertAdapterX
import com.example.converter.adaptersCurrencies.ItemModelX
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class TabLayoutFragmentFavoritesAll : Fragment() {
    private lateinit var binding: FragmentTabLayoutFavoritesAllBinding
    private lateinit var adapterX: ConvertAdapterX





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerFavoriteAll.apply {
            layoutManager = LinearLayoutManager(activity)
            adapterX = ConvertAdapterX()
            binding.recyclerFavoriteAll.adapter = adapterX
        }
        val allCurrencys =  MyCustomApplicationClass.ModelPreferencesManager.get<List<ItemModelX>>("KEY_ONE").orEmpty()
        Log.d("qwerty", "${allCurrencys::class.java}")
        adapterX.submitList(allCurrencys)
    }

    companion object {
        fun newInstance() = TabLayoutFragmentFavoritesAll()
    }
}