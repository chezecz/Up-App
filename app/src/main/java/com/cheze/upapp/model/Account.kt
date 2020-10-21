package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val type: String,
    val id: String,
    val attr: AccAttr
) {
}