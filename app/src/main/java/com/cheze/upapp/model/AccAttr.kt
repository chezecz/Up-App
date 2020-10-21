package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class AccAttr(
    val name: String,
    val type: String,
    val balance: Balance
) {
}