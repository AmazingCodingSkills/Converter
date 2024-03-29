package com.example.calculator.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.converter.core.currency.domain.CurrencyItem
import com.example.calculator.R
import com.example.calculator.databinding.ListItemConverterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal class CalculatorAdapter(val onClick: suspend CoroutineScope.(CurrencyItem) -> Unit)  :
    RecyclerView.Adapter<CalculatorAdapter.NewMyViewHolder>() {

    private var currencyList: List<CurrencyItem> = mutableListOf()
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

        fun init(coroutineScope: CoroutineScope) {
            itemView.setOnClickListener {
                coroutineScope.launch {
                    onClick(country)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(currencyList: List<CurrencyItem>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
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
        holder.init(GlobalScope)

    }
}