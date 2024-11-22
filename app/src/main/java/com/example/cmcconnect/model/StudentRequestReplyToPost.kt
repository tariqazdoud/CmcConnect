package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentRequestReplyToPost (
    val response: String,
    val id_teacher_fk: Int,
    val id_student_fk: Int,
    val id_request_fk: Int
): java.io.Serializable