package com.currency.converter.features.rate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R
import com.example.converter.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class LatestRatesAdapter : ListAdapter<RateItem, LatestRatesAdapter.Holder>(Comparator()) { // по форме записи еще раз
    class Holder(view: View) : RecyclerView.ViewHolder(view) { // ??
        val binding = ListItemBinding.bind(view)
        fun bind(item: RateItem) = with(binding) {
            date.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(item.date)
            currencyName.text = item.baseCurrencyName
            nowValueText.text = item.referenceCurrency.name
            rightValue.text = item.referenceCurrency.value.toString()
        }
    }

    class Comparator : DiffUtil.ItemCallback<RateItem>() { // ??
        override fun areItemsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RateItem, newItem: RateItem): Boolean {
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
