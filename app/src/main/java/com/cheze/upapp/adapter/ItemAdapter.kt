package com.cheze.upapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cheze.upapp.R
import com.cheze.upapp.model.Account

class ItemAdapter(
    private val context: Context,
    private val dataset: List<Account>
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textTitleView: TextView = view.findViewById(R.id.item_title)
        val textDescriptionView: TextView = view.findViewById(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        with (item) {
            with (attr) {
                holder.textTitleView.text = name
                holder.textDescriptionView.text = "${balance.currency} ${balance.value}"
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}