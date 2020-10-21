package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BankAttr(
    val name: String?,
    val type: String?,
    val balance: MoneyObject?,
    val status: String?,
    val rawText: String?,
    val description: String?,
    val message: String?,
//    val holdInfo: String?,
//    val roundUp: MoneyObject?,
    val amount: MoneyObject?,
    val foreignAmount: MoneyObject?,
) {
}