package com.example.converter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.converter.databinding.FragmentTabLayoutFavoritesAllBinding


class TabLayoutFragmentFavoritesAll : Fragment() {
private lateinit var binding: FragmentTabLayoutFavoritesAllBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabLayoutFavoritesAllBinding.inflate(inflater, container, false)
        return binding.root
    }



        companion object {
            fun newInstance() = TabLayoutFragmentFavoritesAll()
        }
}