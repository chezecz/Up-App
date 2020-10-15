package com.cheze.upapp.data

import android.content.Context
import com.cheze.upapp.R
import com.cheze.upapp.model.AccAttr
import com.cheze.upapp.model.Account
import com.cheze.upapp.model.Balance

private const val TAG = "MyActivity"

class DataSource(context: Context) {
    private val secretValue = context.getString(R.string.up_api_key)
    private val baseUrl = "https://api.up.com.au/api/v1"


    fun loadAccounts(): List<Account> {

        val accountsUrl = "$baseUrl/accounts"

        val balance = Balance(currency = "AUD", value = "1.00", valueUnits = 100)
        val accAttr = AccAttr(name = "Up Account", type = "Transactional", balance = balance)
        val account1 = Account(type = "accounts", attr = accAttr, id = "r1")
        val account2 = Account(type = "accounts", attr = accAttr, id = "r2")
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, accountsUrl, null,
//            { error ->
//                println("Response: %s".format(error.toString()))
//                Log.d(TAG, "loadAccounts: $error")
//            },
//            { response ->
//                println("Response: %s".format(response.toString()))
//                Log.d(TAG, "loadAccounts: $response")
//            })
//        println(jsonObjectRequest.toString())
//
        return listOf<Account>(account1, account2)
    }
}