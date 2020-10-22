package com.cheze.upapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cheze.upapp.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ACCOUNT_ID = "account-id"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionFragment : Fragment() {
    private var accountId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.getString(ACCOUNT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param accountId Parameter Bank User ID.
         * @return A new instance of fragment TransactionFragment.
         */
        @JvmStatic
        fun newInstance(accountId: String) =
            TransactionFragment().apply {
                arguments = Bundle().apply {
                    putString(ACCOUNT_ID, accountId)
                }
            }
    }
}