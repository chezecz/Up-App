package com.cheze.upapp.data

import android.content.Context
import com.android.volley.toolbox.Volley
import com.cheze.upapp.R
import com.cheze.upapp.model.BankAttr
import com.cheze.upapp.model.BankObject
import com.cheze.upapp.model.MoneyObject
import com.cheze.upapp.service.RequestWithHeaders
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

class DataSource(private val context: Context) {
    private val secretValue = context.getString(R.string.up_api_key)
    private val baseUrl = context.getString(R.string.api_url)

    suspend fun loadAccounts() = suspendCoroutine<JSONObject> { cont ->

        val accountsUrl = "$baseUrl/accounts"

        val queue = Volley.newRequestQueue(this.context)

        RequestWithHeaders(accountsUrl, cont, secretValue).also {
            it.setShouldCache(false)
            queue.add(it)
        }

    }

    suspend fun loadTransactions(accountId: String) = suspendCoroutine<JSONObject> { cont ->

        val transactionUrl = "$baseUrl/accounts/$accountId/transactions"

        val queue = Volley.newRequestQueue(this.context)

        RequestWithHeaders(transactionUrl, cont, secretValue).also {
            it.setShouldCache(false)
            queue.add(it)
        }
    }
}