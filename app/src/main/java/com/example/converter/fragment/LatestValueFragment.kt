package com.example.converter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.R
import com.example.converter.adapters.ConvertAdapter
import com.example.converter.adapters.Information
import com.example.converter.databinding.*


class LatestValueFragment : Fragment() {


    private lateinit var binding: FragmentLatestValueBinding
    private lateinit var adapter: ConvertAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLatestValueBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }
    private fun initRcView() = with(binding){
        recyclerLatest.layoutManager = LinearLayoutManager(activity)
        adapter = ConvertAdapter()
        recyclerLatest.adapter = adapter
        val list = listOf(
            Information("1", "2", "3"),
            Information("4", "5", "6"),
            Information("7", "8", "9")
        )
        adapter.submitList(list)

    }

    companion object {
        fun newInstance() = LatestValueFragment()
    }
}