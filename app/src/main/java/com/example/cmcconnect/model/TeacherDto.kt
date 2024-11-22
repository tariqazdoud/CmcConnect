package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class TeacherDto (
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val image: String? = null,
    val id_type_user_fk: Int? = null
): java.io.Serializable