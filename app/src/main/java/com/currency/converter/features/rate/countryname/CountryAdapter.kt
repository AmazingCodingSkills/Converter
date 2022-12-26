package com.currency.converter.features.rate.countryname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currency.converter.features.rate.countryname.CountryAdapter.MyViewHolder
import com.example.converter.R
import com.example.converter.databinding.ListItemFavoriteBinding

class CountryAdapter(
    val onClick: (CountryItem) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    /*var countryNames: List<CountryItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/
    private var countryNames: List<CountryItem> = mutableListOf()

    fun addAll(items: List<CountryItem>) {
        countryNames = items
        notifyDataSetChanged()
    }

   inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var binding = ListItemFavoriteBinding.bind(itemView)
        private lateinit var country: CountryItem

        fun bind(item: CountryItem) = with(binding) {
            country = item
            if (item.selected) {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_gold24)
            } else {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            nameCountryForFavorite.text = item.countryModel.nameCountry
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
                .inflate(R.layout.list_item_favorite, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val country =
            countryNames[position]///[position] // вот эту строчку не мог написать 2 дня
        holder.bind(country)
    }

    override fun getItemCount(): Int {
        return countryNames.size
    }
}

