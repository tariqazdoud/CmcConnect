package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminDto (
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val image: String,
    val id_pole_fk: Int,
    val id_type_user_fk: Int
): java.io.Serializable