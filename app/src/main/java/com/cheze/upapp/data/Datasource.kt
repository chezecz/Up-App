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
    private val baseUrl = "https://api.up.com.au/api/v1"

    fun convertJsonToObject(jsonObject: JSONObject): MutableList<BankObject> {
        val listObjects = mutableListOf<BankObject>()
        val jsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {
            val jsonAccount= jsonArray.getJSONObject(i)
            val jsonAccountAttr = jsonAccount.getJSONObject("attributes")
            val jsonAccountBalance = jsonAccountAttr.optJSONObject("balance")
            val jsonAccountAmount = jsonAccountAttr.optJSONObject("amount")
            val bankObject = BankObject(
                type = jsonAccount.getString("type"),
                id = jsonAccount.getString("id"),
                attr = BankAttr(
                    name = jsonAccountAttr.optString("displayName"),
                    type = jsonAccountAttr.optString("accountType"),
                    status = jsonAccountAttr.optString("status"),
                    description = jsonAccountAttr.optString("description"),
                    rawText = jsonAccountAttr.optString("rawText"),
                    message = jsonAccountAttr.optString("message"),
                    balance = jsonAccountBalance?.let {
                        MoneyObject(
                            currency = it.optString("currencyCode"),
                            value = it.optString("value"),
                            valueUnits = it.optInt("valueInBaseUnits"),
                        )
                    },
                    amount = jsonAccountAmount?.let {
                        MoneyObject(
                            currency = it.optString("currencyCode"),
                            value = it.optString("value"),
                            valueUnits = it.optInt("valueInBaseUnits"),
                        )
                    },
                    foreignAmount = null,
                ),
            )
            listObjects.add(bankObject)
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