package com.cheze.upapp.data

import android.content.Context
import com.android.volley.toolbox.Volley
import com.cheze.upapp.R
import com.cheze.upapp.model.AccAttr
import com.cheze.upapp.model.Account
import com.cheze.upapp.model.Balance
import com.cheze.upapp.service.RequestWithHeaders
import org.json.JSONObject
import kotlin.coroutines.suspendCoroutine

class DataSource(private val context: Context) {
    private val secretValue = context.getString(R.string.up_api_key)
    private val baseUrl = "https://api.up.com.au/api/v1"

    fun convertJsonToObject(jsonObject: JSONObject): MutableList<Account> {
        val listObjects = mutableListOf<Account>()
        val jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {
            val jsonAccount: JSONObject = jsonArray.getJSONObject(i)
            val jsonAccountAttr: JSONObject = jsonAccount.getJSONObject("attributes")
            val jsonAccountBalance: JSONObject = jsonAccountAttr.getJSONObject("balance")
            val account = Account(
                type = jsonAccount.getString("type"),
                id = jsonAccount.getString("id"),
                attr = AccAttr(
                    name = jsonAccountAttr.getString("displayName"),
                    type = jsonAccountAttr.getString("accountType"),
                    balance = Balance(
                        currency = jsonAccountBalance.getString("currencyCode"),
                        value = jsonAccountBalance.getString("value"),
                        valueUnits = jsonAccountBalance.getInt("valueInBaseUnits")
                    )
                )
            )
            listObjects.add(account)
        }
        return listObjects
    }

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