package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable

class StudentToSend (
    val name: String,
    val email: String,
    val phone: String,
    val image: String,
    val id_groupe_fk: Int,
    val id_type_user_fk: Int,
): java.io.Serializable