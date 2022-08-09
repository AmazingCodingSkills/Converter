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

class ConvertAdapter: ListAdapter<Information, ConvertAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ListItemBinding.bind(view)
        fun bind (item: Information) = with(binding){
        date.text = item.dateValue
        name.text = item.money
        nowValueText.text = item.nowValue
        }
    }
    class Comparator : DiffUtil.ItemCallback<Information>() {
        override fun areItemsTheSame(oldItem: Information, newItem: Information): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Information, newItem: Information): Boolean {
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