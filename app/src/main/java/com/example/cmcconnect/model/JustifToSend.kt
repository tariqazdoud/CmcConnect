package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class JustifToSend(
    val motif: String,
    val file: String,
    val id_student_fk: Int,
    val id_admin_fk: Int
): java.io.Serializable
