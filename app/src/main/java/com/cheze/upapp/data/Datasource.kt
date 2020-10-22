package com.cheze.upapp.data

import android.content.Context
import com.android.volley.toolbox.Volley
import com.cheze.upapp.R
import com.cheze.upapp.service.RequestWithHeaders
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

class DataSource(private val context: Context) {
    private val secretValue = context.getString(R.string.up_api_key)
    private val baseUrl = context.getString(R.string.api_url)

    suspend fun loadAccounts() = suspendCoroutine<JSONObject> { cont ->

        val accountsUrl = "$baseUrl/${context.getString(R.string.account_endpoint)}"

        val queue = Volley.newRequestQueue(this.context)

        RequestWithHeaders(accountsUrl, cont, secretValue).also {
            it.setShouldCache(false)
            queue.add(it)
        }

    }

    suspend fun loadTransactions(accountId: String) = suspendCoroutine<JSONObject> { cont ->

        val transactionUrl = "$baseUrl${context.getString(R.string.account_endpoint)}/$accountId${context.getString(R.string.transaction_endpoint)}"

        val queue = Volley.newRequestQueue(this.context)

        RequestWithHeaders(transactionUrl, cont, secretValue).also {
            it.setShouldCache(false)
            queue.add(it)
        }
    }
}