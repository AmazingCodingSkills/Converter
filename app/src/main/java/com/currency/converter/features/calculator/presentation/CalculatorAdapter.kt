package com.currency.converter.features.calculator.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currency.converter.base.CurrencyItem
import com.example.converter.R
import com.example.converter.databinding.ListItemConverterBinding

class CalculatorAdapter(val onClick: (CurrencyItem) -> Unit) :
    RecyclerView.Adapter<CalculatorAdapter.NewMyViewHolder>() {

    var currencyList: List<CurrencyItem> = mutableListOf()
    fun addAll(items: List<CurrencyItem>) {
        currencyList = items
    }

    inner class NewMyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding = ListItemConverterBinding.bind(itemView)
        lateinit var country: CurrencyItem

        fun bind(item: CurrencyItem) = with(binding) {
            country = item
            convertCurrency.text = item.currencyName
            convertCodeCurrency.text = item.id
        }

        init {
            itemView.setOnClickListener {
                onClick(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout
                        .list_item_converter, parent, false
                )
        return NewMyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: NewMyViewHolder, position: Int) {
        val country = currencyList[position]
        holder.bind(country)
    }
}