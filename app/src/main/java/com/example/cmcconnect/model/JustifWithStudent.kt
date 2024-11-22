package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class JustifWithStudent(
    val id: Int,
    val motif: String,
    val file: String,
    val id_student_fk: Int,
    val id_admin_fk: Int?,
    val student: StudentDto,
): java.io.Serializable
