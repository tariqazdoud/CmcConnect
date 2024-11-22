package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class PoleDto (
    val id: Int? = null,
    val name: String? = null
): java.io.Serializable
