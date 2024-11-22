package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentRequestForAdminReplyToPost(
    val response: String,
    val id_admin_fk: Int,
    val id_student_fk: Int,
    val id_request_fk: Int
): java.io.Serializable
