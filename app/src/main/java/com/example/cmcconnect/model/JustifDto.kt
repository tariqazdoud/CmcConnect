package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class JustifDto (
    val id: Int,
    val motif: Int,
    val file: String,
    val id_student_fk: Int,
    val id_admin_fk: Int
): java.io.Serializable
