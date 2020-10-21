package com.cheze.upapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cheze.upapp.model.BankObject

class MyBankAccountRecyclerViewAdapter(
    private val values: List<BankObject>
) : RecyclerView.Adapter<MyBankAccountRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            Log.d("CLICKPOSITION", viewHolder.adapterPosition.toString(4))
//            val mainActivity = MainActivity()
//            mainActivity.getTransactions(ViewHolder(view).adapterPosition)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with (item) {
            with (attr) {
                holder.idView.text = name
                with (balance) {
                    holder.contentView.text = "${this?.currency} ${this?.value}"
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}