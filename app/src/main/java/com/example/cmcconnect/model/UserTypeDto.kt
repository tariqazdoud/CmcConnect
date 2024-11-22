package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTypeDto (
    val id: Int,
    val type: String
): java.io.Serializable