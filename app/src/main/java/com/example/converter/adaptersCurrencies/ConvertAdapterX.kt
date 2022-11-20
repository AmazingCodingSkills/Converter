package com.example.converter.adaptersCurrencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.ListItemFavoriteBinding

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

}