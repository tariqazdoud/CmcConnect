package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto (
    val id: Int,
    val user_email: String?,
    val id_type_user_fk:Int?
): java.io.Serializable
