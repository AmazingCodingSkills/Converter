package com.example.converter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.converter.R


class TabLayoutFragmentFavorites : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_layout_favorites, container, false)
    }
    companion object {
        fun newInstance() = TabLayoutFragmentFavorites()
    }
}