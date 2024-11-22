package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class AnsweredRequestsWithRequestDetails(
    val id: Int,
    val response: String,
    val admin: AdminDto,
    val student: StudentDto,
    val request: RequestDto
): java.io.Serializable
