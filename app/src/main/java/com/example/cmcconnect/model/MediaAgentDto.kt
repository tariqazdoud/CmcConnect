package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaAgentDto (
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val image: String,
): java.io.Serializable
