package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class BankObject(
    val type: String,
    val id: String,
    val attr: BankAttr,
) {
}