package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val type: String,
    val id: String,
    val attr: TrAttr,
) {
}