package com.cheze.upapp.helper

import com.cheze.upapp.model.BankAttr
import com.cheze.upapp.model.BankObject
import com.cheze.upapp.model.MoneyObject
import org.json.JSONObject

class Converter {
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
}