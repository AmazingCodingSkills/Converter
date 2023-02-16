package com.currency.converter.features.rate.countryname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currency.converter.features.rate.countryname.CountryAdapter.MyViewHolder
import com.example.converter.R
import com.example.converter.databinding.ListItemCountryBottomBinding

class CountryAdapter(val onClick: (CountryItem) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    var countryNames: List<CountryItem> = mutableListOf()

    fun addAll(items: List<CountryItem>) {countryNames = items }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding = ListItemCountryBottomBinding.bind(itemView)
        lateinit var country: CountryItem

        fun bind(item: CountryItem) = with(binding) {
            country = item
            if (item.selected) {
                binding.selectCountryBottom.setImageResource(R.drawable.ic_baseline_star_border_blue24)
            }
            else {
                binding.selectCountryBottom.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            nameCountryBottomSheet.text = item.countryModel.nameCountry
            binding.countryFlag.setImageResource(item.countryModel.icon)
        }

        init {
            itemView.setOnClickListener {
                onClick(country)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_country_bottom, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val country = countryNames[position]
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countryNames.size
    }
}

