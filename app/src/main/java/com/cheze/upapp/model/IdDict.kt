package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class IdDict(
    val type: String,
    val id: String,
) {
}