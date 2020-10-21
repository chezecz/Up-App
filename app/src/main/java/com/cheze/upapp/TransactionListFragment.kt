package com.cheze.upapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheze.upapp.model.Transaction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A fragment representing a list of Items.
 */
class TransactionListFragment : Fragment() {

    private var columnCount = 1

    private var data = listOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            data = Json.decodeFromString<List<Transaction>>(it.getSerializable(ARG_DATA_TR).toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyTransactionListRecyclerViewAdapter(listOf<Transaction>())
            }
        }
        return view
    }

    companion object {

        private const val ARG_DATA_TR = "data"

        @JvmStatic
        fun newInstance(transactions: List<Transaction>) =
            TransactionListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATA_TR, Json.encodeToString(transactions))
                }
            }
    }
}