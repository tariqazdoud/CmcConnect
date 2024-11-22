package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestWithStudent(
    val id: Int,
    val motif: String,
    val id_student_fk: Int,
    val id_teacher_fk: Int?,
    val id_type_request_fk: Int,
    val id_admin_fk: Int?,
    val student: StudentDto,
    val type_request: RequestTypeDto
): java.io.Serializable
