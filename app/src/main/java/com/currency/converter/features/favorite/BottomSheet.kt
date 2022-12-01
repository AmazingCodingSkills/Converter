package com.example.converter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.converter.R
import com.currency.converter.features.rate.countryname.CountryAdapter
import com.example.converter.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCountry()
    }
    private fun initCountry(){
        binding.apply {
            recyclerFavorite.layoutManager = LinearLayoutManager(activity)
            recyclerFavorite.adapter = CountryAdapter(getCountryList())
            getCountryList()
        }
    }
    private fun getCountryList(): List<String> {
        return this.resources.getStringArray(R.array.countries_list).toList()
    }


    /*private fun initListView() = with(binding) {

        val countries: Array<String> = resources.getStringArray(R.array.countries_list)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.activity_list_item, countries)

        listItemCountries.adapter = arrayAdapter
        listItemCountries.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedItem = adapterView.getItemAtPosition(position) as String
                val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            }
    }*/


}