package com.cheze.upapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Relationship(
    val account: DataDict,
) {
}