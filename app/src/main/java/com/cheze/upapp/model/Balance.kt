package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    val currency: String,
    val value: String,
    val valueUnits: Int
) {
}