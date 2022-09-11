package com.example.converter.adaptersCurrencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.*

class ConvertAdapterX : ListAdapter<ItemModelX, ConvertAdapterX.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)
        fun bind(item: ItemModelX) = with(binding) {
            date.text = item.countries.toString()
            currencyName.text = item.code
            nowValueText.text = item.name
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}