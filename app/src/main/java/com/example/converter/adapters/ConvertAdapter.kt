package com.example.converter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.*
import kotlin.Comparator

class ConvertAdapter: ListAdapter<Response, ConvertAdapter.Holder>(Comparator()) {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val binding = ListItemBinding.bind(view)
        fun bind (item: Response) = with(binding){
        date.text = item.date
        currencyName.text = item.base
        nowValueText.text = item.rates.toString() // это
        }
    }
    class Comparator : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem==newItem
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