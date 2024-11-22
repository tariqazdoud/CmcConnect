package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestTypeDto (
    val id: Int,
    val name: String
): java.io.Serializable
