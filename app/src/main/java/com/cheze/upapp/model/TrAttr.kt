package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class TrAttr(
    val status: String,
//    val rawText: String?,
    val description: String,
//    val message: String?,
//    val holdInfo: String?,
//    val roundUp: String?,
    val amount: Balance,
    val foreignAmount: Balance?,
) {
}