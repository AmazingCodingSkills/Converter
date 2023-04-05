package com.example.rate.presentation.latestrates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.converter.core.data.currencyrate.RateItem
import com.example.rate.R
import com.example.rate.databinding.ListItemBinding
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

internal class LatestRatesAdapter(val onClick: (RateItem) -> Unit) :
    ListAdapter<RateItem, LatestRatesAdapter.Holder>(
        Comparator()
    ) {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)

        fun bind(item: RateItem) = with(binding) {
            date.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(item.date)
            baseCurrencyName.text = item.baseCurrencyName
            selectedCurrency.text = item.referenceCurrency.name
            val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
                decimalSeparator = '.'
            }
            val entryFormat = DecimalFormat("#####.####", symbols)
            currentRate.text = entryFormat.format(item.referenceCurrency.value)
        }

        init {
            itemView.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

    }

    class Comparator : DiffUtil.ItemCallback<RateItem>() {
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
