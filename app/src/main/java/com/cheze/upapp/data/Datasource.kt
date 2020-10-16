package com.cheze.upapp.data

import android.content.Context
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.cheze.upapp.R
import com.cheze.upapp.model.AccAttr
import com.cheze.upapp.model.Account
import com.cheze.upapp.model.Balance
import com.cheze.upapp.service.VolleyService
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "MyActivity"

class DataSource(private val context: Context) {
    private val secretValue = context.getString(R.string.up_api_key)
    private val baseUrl = "https://api.up.com.au/api/v1"

    private fun convertJsonToObject(jsonArray: JSONArray): MutableList<Account> {
        val listObjects = mutableListOf<Account>()
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

    fun loadAccounts(): MutableList<Account> {

        val accountsUrl = "$baseUrl/accounts"

        var listAccounts: MutableList<Account> = mutableListOf<Account>()

        val request = object: JsonObjectRequest(
            Method.GET, accountsUrl, null,
            { response ->
                val accounts = response
                    .getJSONArray("data")
                listAccounts = convertJsonToObject(accounts)
            },
            {
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show()
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = secretValue
                return headers
            }
        }

        VolleyService.requestQueue.add(request)
        VolleyService.requestQueue.start()

        return listAccounts
    }
}