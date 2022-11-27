package com.example.converter.countryname

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.converter.R

class CountryAdapter(private val countryNames: List<String>) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameCountries: TextView = itemView.findViewById(R.id.nameCountries)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_favorite, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameCountries.text = countryNames[position]
    }

    override fun getItemCount(): Int {
        return countryNames.size
    }
}
