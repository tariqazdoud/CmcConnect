package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestDto (
    val id: Int,
    val motif: String,
    val id_student_fk: Int,
    val id_request_type_fk: Int? = null,
    val id_teacher_fk: Int?,
    val id_admin_fk: Int?,
    val answered: Boolean
): java.io.Serializable
