package com.currency.converter.features.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.ListItemFavoriteBinding


// ListAdapter (immutable, diff-util, additional methods)

// Recycler.Adapter


// Сущность для работы со списком
// SOLID, OOP+
// S - single responsibility principle +++

class CurrenciesAdapter constructor(private val onItemClickListener: (CurrencyItem) -> Unit) :
    ListAdapter<CurrencyItem, CurrenciesAdapter.Holder>(Comparator()) {

    class Holder(view: View, private val onItemClickListener: (CurrencyItem) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ListItemFavoriteBinding.bind(view)
        private lateinit var currency: CurrencyItem

        // Вызывается один раз при создании класса +++
        init {
            binding.favoriteImageButton.setOnClickListener {
                onItemClickListener(currency)
            }
        }

        fun bind(item: CurrencyItem) = with(binding) {
            currency = item
            if (item.isFavorite) {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_gold24)
            } else {
                binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
            nameCountryForFavorite.text = item.currencyName
        }
    }


    class Comparator : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem  //equals and hashcode
        }

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_favorite, parent, false)
            return Holder(view, onItemClickListener)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(getItem(position))

        }
    }



