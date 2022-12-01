package com.currency.converter.features.favorite

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.ListItemFavoriteBinding

class CurrenciesAdapter constructor(private val onItemClickListener: (CurrencyItem) -> Unit) :
    ListAdapter<CurrencyItem, CurrenciesAdapter.Holder>(Comparator()) {

    class Holder(view: View, private val onItemClickListener: (CurrencyItem) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ListItemFavoriteBinding.bind(view)
        private lateinit var currency: CurrencyItem
        private val prefsAdapterX: SharedPreferences =
            itemView.context.getSharedPreferences(AdapterXPreference, MODE_PRIVATE)

        init {
            binding.favoriteImageButton.setOnClickListener {
                onItemClickListener.invoke(currency)
                //prefsAdapterX.edit().putBoolean("prefs", true).apply()
                //   binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_gold24)
            }
        }

        fun bind(item: CurrencyItem) = with(binding) {

            favoriteImageButton.setOnClickListener {
                item.isFavorite = true
                val value = item.isFavorite
                saveUserDataAdapterX(value = value)
                Log.d("opach", "записаны данные")
                if (item.isFavorite) {
                    binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_gold24)
                } else {
                    binding.favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
                item.isFavorite = !item.isFavorite
            }
            loadUserData()
            Log.d("opach", "данные получены")
            currency = item
            nameCountryForFavorite.text = item.currencyName
        }
        private fun saveUserDataAdapterX(value: Boolean) {
            prefsAdapterX.edit()
                .putBoolean(Value, value)
                .apply()
        }

        private fun loadUserData() {
            prefsAdapterX.getBoolean(Value, true)
                binding.apply{
                    favoriteImageButton.setImageResource(R.drawable.ic_baseline_star_border_24)
                }//????
        }

        private companion object {
            const val AdapterXPreference = "AdapterXPreference"
            const val Value = "ValueKey"
        }
    }

    class Comparator : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite, parent, false)
        return Holder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))

    }


}

/*
class ConvertAdapterX constructor(
    private val onItemClickListener: (ItemModelX) -> Unit
) : ListAdapter<ItemModelX, ConvertAdapterX.Holder>(Comparator()) {

    class Holder(view: View, private val onItemClickListener: (ItemModelX) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ListItemFavoriteBinding.bind(view)
        private lateinit var currency: ItemModelX

        init {
            binding.root.setOnClickListener {
                currency.let { onItemClickListener.invoke(currency) }
            }
        }

        fun bind(item: ItemModelX) = with(binding) {
            currency = item
            nameCountryForFavorite.text = item.countries.toString()
        }
    }

    class Comparator : DiffUtil.ItemCallback<ItemModelX>() {
        override fun areItemsTheSame(oldItem: ItemModelX, newItem: ItemModelX): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemModelX, newItem: ItemModelX): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite, parent, false)
        return Holder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

}*/
